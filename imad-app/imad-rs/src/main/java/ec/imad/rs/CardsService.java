package ec.imad.rs;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import ec.imad.jpa.dao.Top5ProductsDao;
import ec.imad.jpa.dao.CurrentStateOfStockDao;
import ec.imad.jpa.dao.StockDao;
import ec.imad.jpa.dao.ProductDao;
import ec.imad.jpa.dao.TotalStockCategoryDao;
import ec.imad.jpa.dao.OverviewStockingIssuesDao;
import ec.imad.jpa.dao.CombinedOutOfStockPercentageDao;
import ec.imad.jpa.dao.CombinedOutOfStockHeaderDao;

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
    @Path("/card1Title")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTotalStockValueTitle() {
        JSONObject item = new JSONObject();
        item.put("n", "$77,979");
        item.put("u", "CAD");
        item.put("trend", "Up");
        item.put("valueColor", "Good");
        item.put("details", "as of Dec 12, 2022");
        return "{\"results\":" + item.toString()+ "}";
    }

    /*
     * **********************************************************
     *  PENDING IT IS NOT WORKING.
     */
    @GET
    @Path("/card1List")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTotalStockValueList() {
        List<TotalStockValue> totalStockValues = totalStockValueDao.getAll();
        Iterator<TotalStockValue> iterator = totalStockValues.iterator();
        JSONArray array = new JSONArray();
        JSONObject item = null;

        String currentLocation = null;

        while(iterator.hasNext()){
            item = new JSONObject();
            TotalStockValue obj = iterator.next();
            currentLocation = obj.getCity();
            item.put("Category", currentLocation);
            item.put(obj.getCategoryName(), obj.getTotalCategory()); 

            // array.put(item);
            while(iterator.hasNext()){
                obj = iterator.next();
                
                if(!currentLocation.equals(obj.getCity())) {  
                    array.put(item);
                    // item = new JSONObject();
                    item.put("Category", obj.getCity());
                    currentLocation = obj.getCity();
                } else {
                    // item = new JSONObject();
                    item.put(obj.getCategoryName(), obj.getTotalCategory()); 
                }
            }
        }
        return "{\"results\":" + array.toString() + "}";
    }

    @GET
    @Path("/card1Measures")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTotalStockValueMeasures() {
        List<TotalStockCategory> categories = totalStockCategoryDao.getAll();
        JSONArray array = new JSONArray();
        for (TotalStockCategory category : categories) {
            JSONObject item = new JSONObject();
            item.put("label", category.getName());
            item.put("value", "{"+category.getName().replaceAll("\\s+","")+"}");
            array.put(item);
        }
        return "{\"results\":" + array.toString()+ "}";
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