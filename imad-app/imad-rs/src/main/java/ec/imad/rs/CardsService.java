package ec.imad.rs;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import ec.imad.business.ProcessingScenariosStatelessLocal;
import ec.imad.jpa.dao.CombinedOutOfStockHeaderDao;
import ec.imad.jpa.dao.CombinedOutOfStockPercentageDao;
import ec.imad.jpa.dao.CurrentStateOfStockDao;
import ec.imad.jpa.dao.OverviewStockingIssuesDao;
import ec.imad.jpa.dao.StockDao;
import ec.imad.jpa.dao.ProductDao;
import ec.imad.jpa.dao.Top5ProductsDao;
import ec.imad.jpa.dao.TotalStockCategoryDao;
import ec.imad.jpa.dao.TotalStockValueDao;
import ec.imad.jpa.model.Stock;
import ec.imad.jpa.model.Product;
import ec.imad.jpa.model.TotalStockCategory;
import ec.imad.jpa.model.TotalStockValue;

import ec.imad.jpa.model.OverviewStockingIssues;

@Path("/")
@RequestScoped
public class CardsService {

    @EJB
    private TotalStockCategoryDao totalStockCategoryDao;

    @EJB
    private Top5ProductsDao top5ProductsDao;

    @EJB
    private CurrentStateOfStockDao currentStateOfStockDao;
    @EJB
    private OverviewStockingIssuesDao overviewStockingIssuesDao;

    @EJB
    private CombinedOutOfStockPercentageDao combinedOutOfStockPercentageDao;

    @EJB
    private CombinedOutOfStockHeaderDao combinedOutOfStockHeaderDao;

    @EJB
    private TotalStockValueDao totalStockValueDao;

    @EJB
    private StockDao stockDao;

    @EJB
    private ProductDao productDao;

    @EJB
    private ProcessingScenariosStatelessLocal processingScenariosStatelessLocal;

    @GET
    @Path("/cardProcessing")
    @Produces(MediaType.APPLICATION_JSON)
    public String processingScenarios() {
        processingScenariosStatelessLocal.calculateTotalStockValueByCategory();
        return "{\"results\": \"process started\"}";
    }

    /*
     * Donna this method below is for testing before create the EJB Stateless.
     * I left here and you can use for your development.
     * We should delete later. 
     * 
     */
    @GET
    @Path("/cardX")
    @Produces(MediaType.APPLICATION_JSON)
    public String testingJPA() {

        // CORPORATE (GLOBAL) OoS/NOoS percentages

        Map<Integer, Integer> stockMap = new HashMap<Integer, Integer>();
        List<Product> allProduct = productDao.getAll();
        List<Stock> allStock = stockDao.getAll();

        Map<Integer, Integer> productMap = new HashMap<Integer, Integer>();
        List<Integer> globalProductIds = new ArrayList<Integer>();
        List<Integer> productIds = new ArrayList<Integer>();


        int countOfAllProductsCarriedGlobally = 0;
        int countOfAllProductsOutOfStock = 0;
        int countOfAllProductsNearlyOutOfStock = 0;


        //get all products (globally)
        countOfAllProductsCarriedGlobally = allProduct.size();

        for(Product product : allProduct) {
            Integer productId = product.getId();
            globalProductIds.add(productId);
            productMap.put(productId, product.getGlobalReorderPoint());
        }

        //get all stock
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


        //return "OoS % = " + percentageOutOfStock;
        // return stockMap.toString() + " ...>>> " + countOfAllProductsCarriedGlobally + " .. " + countOfAllProductsOutOfStock;

        OverviewStockingIssues overviewStockingIssues = new OverviewStockingIssues(percentageOutOfStock, percentageNearlyOutOfStock);
        overviewStockingIssuesDao.saveModel(overviewStockingIssues);
        return "{\"results\":" + overviewStockingIssues + "}";



        // assign to object to save at A table.
        // List<OverviewStockingIssues> overviewStockingIssues = new ArrayList<OverviewStockingIssues>();
        // hm.entrySet().forEach(entry -> {
            // OverviewStockingIssues osi = new OverviewStockingIssues(entry.getPercentageOutOfStock(), entry.getPercentageNearlyOutOfStock());
            // overviewStockingIssues.add(osi);
            // System.out.println(entry.getPercentageOutOfStock() + " " + entry.getPercentageNearlyOutOfStock());
        // });
        //overviewStockingIssuesDao.saveModel(overviewStockingIssues);

        // return "{\"results\":" + overviewStockingIssues + "}";


        /*
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

        // assign to object to save at A table.
        List<TotalStockCategory> totalStockCategories = new ArrayList<TotalStockCategory>();
        hm.entrySet().forEach(entry -> {
            TotalStockCategory tsc = new TotalStockCategory(entry.getKey(), entry.getValue());
            totalStockCategories.add(tsc);
            System.out.println(entry.getKey() + " " + entry.getValue());
        });
        totalStockCategoryDao.saveModel(totalStockCategories);

        return "{\"results\":" + totalStockCategories + "}";

        */

    }

    @GET
    @Path("/card1")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTotalStockValueList() {
        
        List<TotalStockValue> totalStockValues = totalStockValueDao.getAll();
        Map<String, Map<String, BigDecimal>> locationMap = new HashMap<String, Map<String, BigDecimal>>();

        // 1- set list ******************
        //get all locations
        for (TotalStockValue totalStockValue : totalStockValues) {
            String location = totalStockValue.getCity();
            locationMap.put(location, new HashMap<String, BigDecimal>());
        }

        // process and set categories per location
        BigDecimal totalValue = new BigDecimal(0);
        for (TotalStockValue totalStockValue : totalStockValues) {
            String location = totalStockValue.getCity();
            if(locationMap.containsKey(location)){
                Map<String, BigDecimal> locationCategories = locationMap.get(location);
                locationCategories.put(totalStockValue.getCategoryName(),  totalStockValue.getTotalCategory());
                locationMap.put(location, locationCategories);
                totalValue = totalValue.add(totalStockValue.getTotalCategory());
            }
        }

        // transform from Java to JSON
        JSONArray list = new JSONArray();
        locationMap.entrySet().forEach(location -> {
            JSONObject item = new JSONObject();
            Map<String, BigDecimal> categories = location.getValue();
            categories.entrySet().forEach(category -> {
                item.put(category.getKey(), category.getValue());
            });
            item.put("Category", location.getKey());
            list.put(item);
        });

        // 2- set header ******************
        JSONObject header = new JSONObject();
        header.put("n", "$" + totalValue);
        header.put("u", "CAD");
        header.put("trend", "Up");
        header.put("valueColor", "Good");
        header.put("details", "as of Dec 16, 2022");

        // 3- set measures ******************
        List<TotalStockCategory> categories = totalStockCategoryDao.getAll();
        JSONArray measures = new JSONArray();
        for (TotalStockCategory category : categories) {
            JSONObject item = new JSONObject();
            item.put("label", category.getName());
            item.put("value", "{"+category.getName().replaceAll("\\s+","")+"}");
            measures.put(item);
        }

        // sety body: 2, 1, 3
        JSONObject body = new JSONObject();
        body.put("header", header);
        body.put("list", list);
        body.put("measures", measures);

        JSONObject results = new JSONObject();
        results.put("results", body);
        return results.toString();
    }

    @GET
    @Path("/card2")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTotalStockValueByCategory() {
        return "{\"results\":" + totalStockCategoryDao.getAll() + "}";
    }

    @GET
    @Path("/card3")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTop5Products() {
        return "{\"results\":" + top5ProductsDao.getAll() + "}";
    }


    @GET
    @Path("/card4")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOverviewStockingIssues() {
        return "{\"results\":" + overviewStockingIssuesDao.getAll() + "}";
    }


    @GET
    @Path("/card5")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCurrentStateOfStock() {
        return "{\"results\":" + currentStateOfStockDao.getAll() + "}";
    }


    @GET
    @Path("/card6")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCombinedOutOfStockPercentage() {
        return "{\"results\":" + combinedOutOfStockPercentageDao.getAll() + "}";
    }

    @GET
    @Path("/card6Header")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCombinedOutOfStockHeader() {
        return "{\"results\":" + combinedOutOfStockHeaderDao.getAll() + "}";
    }
}