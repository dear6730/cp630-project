package ec.imad.business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import ec.imad.jpa.dao.StockDao;
import ec.imad.jpa.dao.Top5ProductsDao;
import ec.imad.jpa.dao.ProductDao;

import ec.imad.jpa.dao.TotalStockCategoryDao;
import ec.imad.jpa.dao.TotalStockValueDao;
import ec.imad.jpa.dao.OverviewStockingIssuesDao;
import ec.imad.jpa.dao.CurrentStateOfStockDao;

import ec.imad.jpa.model.Stock;
import ec.imad.jpa.model.Top5Products;
import ec.imad.jpa.model.Product;

import ec.imad.jpa.model.TotalStockCategory;
import ec.imad.jpa.model.TotalStockValue;
import ec.imad.jpa.model.OverviewStockingIssues;
import ec.imad.jpa.model.CurrentStateOfStock;

@Stateless
@LocalBean
public class ProcessingScenariosStateless 
    implements ProcessingScenariosStatelessLocal, 
                    ProcessingScenariosStatelessRemote {
    private static final Logger LOGGER = Logger.getLogger(ProcessingScenariosStateless.class);

    @PersistenceContext(unitName="primary")
    private EntityManager entityManager;

    @EJB
    private StockDao stockDao;

    @EJB
    private TotalStockCategoryDao totalStockCategoryDao;

    @EJB
    private ProductDao productDao;

    @EJB 
    private OverviewStockingIssuesDao overviewStockingIssuesDao;

    @EJB
    private CurrentStateOfStockDao currentStateOfStockDao;

    @EJB
    private TotalStockValueDao totalStockValueDao;

    @EJB
    private Top5ProductsDao top5ProductsDao;


    /**
     * This method should calculate the value in stock per location and per category.
     * Also it should show the sum of total stock value.
     * 
     * The operational information from transactional database should 
     * be extracted, transformed and load into the analytical database.
     */
    @Override
    public void calculateTotalStockValue() {
        LOGGER.info("Start process calculateTotalStockValueByLocation");
        List<Stock> allStock = stockDao.getAll();
        Map<String, Map<String, BigDecimal>> locationMap = 
            new HashMap<String, Map<String, BigDecimal>>();

        //get all locations
        for (Stock stock : allStock) {
            String location = stock.getLocation().getCity();
            locationMap.put(location, new HashMap<String, BigDecimal>());
        }

        // process and set categories per location
        for (Stock stock : allStock) {
            String location = stock.getLocation().getCity();
            if(locationMap.containsKey(location)){
                Map<String, BigDecimal> locationCategories = locationMap.get(location);
                BigDecimal totalCategory = stock.getProduct().getPrice().multiply(new BigDecimal(stock.getQuantity()));
                locationCategories.put(stock.getProduct().getCategory().getName(),  
                                       totalCategory);
                locationMap.put(location, locationCategories);
            }
        }

        // assign to model 
        List<TotalStockValue> totalStockValues = new ArrayList<TotalStockValue>();
        locationMap.entrySet().forEach(location -> {
            Map<String, BigDecimal> categories = location.getValue();
            categories.entrySet().forEach(category -> {
                TotalStockValue totalStockValue = new TotalStockValue();
                totalStockValue.setCategoryName(category.getKey().replaceAll("\\s+",""));
                totalStockValue.setTotalCategory(category.getValue());
                totalStockValue.setCity(location.getKey());
                totalStockValues.add(totalStockValue);
            });
        });

        // save at A table
        totalStockValueDao.saveModel(totalStockValues);
        LOGGER.info("Finish process calculateTotalStockValueByLocation. Data saved at TotalStockValue.");
    }

    /**
     * This method should calculate the value in stock per category (price * quantity).
     * The operational information from transactional database should 
     * be extracted, transformed and load into the analytical database.
     */
    @Override
    public void calculateTotalStockValueByCategory() {
        LOGGER.info("Start process calculateTotalStockValueByCategory");
        Map<String, BigDecimal> hm = new HashMap<String, BigDecimal>();
        List<Stock> allStock = stockDao.getAll();

        //get all categories
        for (Stock stock : allStock) {
            String catName = stock.getProduct().getCategory().getName();
            hm.put(catName, new BigDecimal(0));
        }

        // process stock value per category
        for (Stock stock : allStock) {
            String catName = stock.getProduct().getCategory().getName();
            if(hm.containsKey(catName)){
                BigDecimal totalStockValue = 
                    stock.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(stock.getQuantity()));
                BigDecimal currentValue = hm.get(catName);
                totalStockValue = totalStockValue.add(currentValue);
                hm.put(catName, totalStockValue);
            }
        }

        // assign to model 
        List<TotalStockCategory> totalStockCategories = new ArrayList<TotalStockCategory>();
        hm.entrySet().forEach(entry -> {
            TotalStockCategory tsc = new TotalStockCategory(entry.getKey(), entry.getValue());
            totalStockCategories.add(tsc);
        });

        // save at A table
        totalStockCategoryDao.saveModel(totalStockCategories);
        LOGGER.info("Finish process calculateTotalStockValueByCategory. Data saved at TotalStockCategory.");
    }

    /**
     * This method should calculate the top 5 products value in stock.
     * The operational information from transactional database should 
     * be extracted, transformed and load into the analytical database.
     */
    @Override
    public void calculateTop5StockValueProducts() {

        LOGGER.info("Start process calculateTop5StockValueProducts");
        List<Top5Products> top5StockValueProducts = productDao.getTop5StockValueProducts();
        top5ProductsDao.saveModel(top5StockValueProducts);
        LOGGER.info("Finish process calculateTop5StockValueProducts. Data saved at Top5Products.");
    }

    /**
     * This method should calculate the GLOBAL percentages for products out of stock and nearly out of stock,
     * showing a naive overview of the stocking issues within the ENTIRE system. 
     * 
     * Products out of stock percentage is calculated by:
     *    COUNT(products in system with quantity=0)/COUNT(all products in the system)
     * Products nearly out of stock percentage is calculated by:
     *    COUNT(products in system with quantity <= GLOBAL reorder point)/COUNT(all products in the system)
     *
     * The operational information from transactional database should 
     * be extracted, transformed and load into the analytical database.
     */
    @Override
    public void calculateOverviewStockingIssues() {
        LOGGER.info("Start process calculateOverviewStockingIssues");

        // CORPORATE (GLOBAL) OoS/NOoS percentages

        Map<Integer, Integer> stockMap = new HashMap<Integer, Integer>();
        List<Stock> allStock = stockDao.getAll();
        List<Integer> productIds = new ArrayList<Integer>();

        Map<Integer, Integer> productMap = new HashMap<Integer, Integer>();
        List<Product> allProduct = productDao.getAll();
        List<Integer> globalProductIds = new ArrayList<Integer>();

        int countOfAllProductsCarriedGlobally = 0;
        int countOfAllProductsOutOfStock = 0;
        int countOfAllProductsNearlyOutOfStock = 0;

        //get all products (globally)
        countOfAllProductsCarriedGlobally = allProduct.size();

        // get all products & global reorder point
        for(Product product : allProduct) {
            Integer productId = product.getId();
            globalProductIds.add(productId);
            productMap.put(productId, product.getGlobalReorderPoint());
        }

        //get all stock & quantity
        for (Stock stock : allStock) {
            Integer productId = stock.getProduct().getId();
            productIds.add(productId);
            Integer quantity = stockMap.containsKey(productId) ? stockMap.get(productId) : 0;
            quantity += stock.getQuantity();
            stockMap.put(productId, quantity);
        }

        //count all products that are not in stock table
        globalProductIds.removeAll(productIds);
        countOfAllProductsOutOfStock += globalProductIds.size();

        //count all products with global stock count of zero (in stock table)
        countOfAllProductsOutOfStock += stockMap.values().stream().filter(v -> v == 0).count();

        //count all products with global stock count <= global_reorder_point (in stock table)
        for (Integer productId : stockMap.keySet()) {
            Integer quantity = stockMap.get(productId);
            Integer globalReorderPoint = productMap.get(productId);
            if(quantity > 0 && quantity <= globalReorderPoint) {
                countOfAllProductsNearlyOutOfStock += 1;
            }
        }

        // READY TO WRITE countOfAllProductsOutOfStock and countOfAllProductsNearlyOutOfStock to A table
        BigDecimal percentageOutOfStock = new BigDecimal(countOfAllProductsOutOfStock/(double)countOfAllProductsCarriedGlobally*100.0);
        percentageOutOfStock = percentageOutOfStock.setScale(2, RoundingMode.HALF_EVEN);

        BigDecimal percentageNearlyOutOfStock = new BigDecimal(countOfAllProductsNearlyOutOfStock/(double)countOfAllProductsCarriedGlobally*100.0);
        percentageNearlyOutOfStock = percentageNearlyOutOfStock.setScale(2, RoundingMode.HALF_EVEN);

        OverviewStockingIssues overviewStockingIssues = new OverviewStockingIssues(percentageOutOfStock, percentageNearlyOutOfStock);
        overviewStockingIssuesDao.saveModel(overviewStockingIssues);

        LOGGER.info("Finish process calculateOverviewStockingIssues. Data saved at OverviewStockingIssues.");
    }

    /**
     * This method should generate a GLOBAL list of products out of stock and nearly out of stock,
     * showing a naive overview of the stocking issues within the ENTIRE system. 
     * 
     * The operational information from transactional database should 
     * be extracted, transformed and load into the analytical database.
     */
    @Override
    public void generateCurrentStateOfStockList() {
        LOGGER.info("Start process generateCurrentStateOfStockList");

        // CORPORATE (GLOBAL) OoS/NOoS product list

        Map<Integer, Integer> stockMap = new HashMap<Integer, Integer>();
        List<Stock> allStock = stockDao.getAll();
        List<Integer> productIds = new ArrayList<Integer>();
        List<Product> allProduct = productDao.getAll();

        //get all stock
        for (Stock stock : allStock) {
            Integer productId = stock.getProduct().getId();
            productIds.add(productId);
            Integer quantity = stockMap.containsKey(productId) ? stockMap.get(productId) : 0;
            quantity += stock.getQuantity();
            stockMap.put(productId, quantity);
        }

        //look at all products
        List<CurrentStateOfStock> stateOfStock = new ArrayList<CurrentStateOfStock>();
        Integer globalQuantity = 0;

        for (Product product : allProduct) {
            globalQuantity = stockMap.get(product.getId());

            if(globalQuantity == null || globalQuantity == 0) {
                stateOfStock.add( new CurrentStateOfStock(
                    product.getSku(),
                    product.getName(),
                    0,
                    "Out of Stock",
                    "Error"
                ));   
            } else if(globalQuantity <= product.getGlobalReorderPoint()) {
                stateOfStock.add( new CurrentStateOfStock(
                    product.getSku(),
                    product.getName(),
                    globalQuantity,
                    "Nearly Out",
                    "Warning"
                ));
            }
        }

        currentStateOfStockDao.saveModel(stateOfStock);

        LOGGER.info("Finish process generateCurrentStateOfStockList. Data saved at CurrentStateOfStock.");
    }
}