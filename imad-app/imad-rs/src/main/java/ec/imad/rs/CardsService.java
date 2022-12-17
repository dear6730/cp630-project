package ec.imad.rs;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
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
import ec.imad.jpa.dao.ProductDao;
import ec.imad.jpa.dao.StockDao;
import ec.imad.jpa.dao.Top5ProductsDao;
import ec.imad.jpa.dao.TotalStockCategoryDao;
import ec.imad.jpa.dao.TotalStockValueDao;
import ec.imad.jpa.model.TotalStockCategory;
import ec.imad.jpa.model.TotalStockValue;

import ec.imad.jpa.model.Product;
import ec.imad.jpa.model.Stock;

import ec.imad.jpa.model.OverviewStockingIssues;
import ec.imad.jpa.model.CurrentStateOfStock;

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
        processingScenariosStatelessLocal.calculateTotalStockValue();
        processingScenariosStatelessLocal.calculateTotalStockValueByCategory();
        processingScenariosStatelessLocal.calculateOverviewStockingIssues();

        return "{\"results\": \"process started\"}";
    }

    /*
     * We should delete later.  
     */
    @GET
    @Path("/cardX")
    @Produces(MediaType.APPLICATION_JSON)
    public String testingJPA() {

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
        return "{\"results\":" + stateOfStock.toString() + "}";
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