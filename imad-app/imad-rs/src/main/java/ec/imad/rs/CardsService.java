package ec.imad.rs;

import java.math.BigDecimal;
import java.util.Date;
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
import ec.imad.jpa.dao.HistoricalStockDao;
import ec.imad.jpa.dao.Top5ProductsDao;
import ec.imad.jpa.dao.TotalStockCategoryDao;
import ec.imad.jpa.dao.TotalStockValueDao;
import ec.imad.jpa.dao.CombinedOutOfStockHeaderDao;
import ec.imad.jpa.dao.CombinedOutOfStockPercentageDao;


import ec.imad.jpa.model.TotalStockCategory;
import ec.imad.jpa.model.TotalStockValue;

import ec.imad.jpa.model.Product;
import ec.imad.jpa.model.Stock;
import ec.imad.jpa.model.HistoricalStock;

import ec.imad.jpa.model.OverviewStockingIssues;
import ec.imad.jpa.model.CurrentStateOfStock;
import ec.imad.jpa.model.CombinedOutOfStockHeader;
import ec.imad.jpa.model.CombinedOutOfStockPercentage;



import ec.imad.business.model.Quarter;

import ec.imad.business.util.PercentageHelper;


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
    private HistoricalStockDao historicalStockDao;

    @EJB
    private ProcessingScenariosStatelessLocal processingScenariosStatelessLocal;

    @GET
    @Path("/cardProcessing")
    @Produces(MediaType.APPLICATION_JSON)
    public String processingScenarios() {
        processingScenariosStatelessLocal.calculateTotalStockValue();
        processingScenariosStatelessLocal.calculateTotalStockValueByCategory();
        processingScenariosStatelessLocal.calculateTop5StockValueProducts();
        processingScenariosStatelessLocal.calculateOverviewStockingIssues();
        processingScenariosStatelessLocal.generateCurrentStateOfStockList();

        // processingScenariosStatelessLocal.calculateCombinedPercentageHistory();

        return "{\"results\": \"process started\"}";
    }

    /*
     * We should delete later.  
     */
    @GET
    @Path("/cardX")
    @Produces(MediaType.APPLICATION_JSON)
    public String testingJPA() {

        // Card 6 work

        // 1: Current Percentage
        PercentageHelper helper = new PercentageHelper(productDao, stockDao);
        BigDecimal currentCombinedPercentage = helper.calculateCombinedPercentage();

        // 2: Get the Historical Data + percentages
        Quarter currentQuarter = new Quarter();

        Quarter oneQuarterAgo = currentQuarter.getPreviousQuarter();
        Quarter twoQuartersAgo = oneQuarterAgo.getPreviousQuarter();

        // get the month values for the DB
        List<Integer> sixMonthListValues = new ArrayList<Integer>();
        for(Integer month : twoQuartersAgo.getMonthListValues()) {
            sixMonthListValues.add(month);
        }
        for(Integer month : oneQuarterAgo.getMonthListValues()) {
            sixMonthListValues.add(month);
        }

        // and for the labels in graph
        List<String> sixMonthListLabels = new ArrayList<String>();
        for(String monthLabel : twoQuartersAgo.getMonthList()) {
            sixMonthListLabels.add(monthLabel);
        } 
        for(String monthLabel : oneQuarterAgo.getMonthList()) {
            sixMonthListLabels.add(monthLabel);
        }


        // get only the requested months of data from the Dao

        // List<HistoricalStock> filteredHistoricalStock = historicalStockDao.(currentQuarter);
        PercentageHelper historicalHelper = new PercentageHelper(currentQuarter, productDao, historicalStockDao, oneQuarterAgo, twoQuartersAgo, sixMonthListValues);


        // and calculate percentages
        List<CombinedOutOfStockPercentage> combinedOutOfStockPercentages = historicalHelper.calculateAllHistoricalPercentages(sixMonthListValues);


        ///TODO: Step 3: NEED TO SET THIS FROM STEP 2
        BigDecimal lastValue = new BigDecimal(10.5); // TEST VALUE FOR NOW


        // 4: set the Header
        CombinedOutOfStockHeader combinedOutOfStockHeader = new CombinedOutOfStockHeader();
        combinedOutOfStockHeader.setNumber(currentCombinedPercentage);

        // use last percentage and compare...

        if( currentCombinedPercentage.compareTo(lastValue) == -1) {
            // current is less than last (generally good)
            combinedOutOfStockHeader.setTrend("Down");
            combinedOutOfStockHeader.setState("Good");

        } else if( currentCombinedPercentage.compareTo(lastValue) == 1) {
            // current is greater than last (generally bad)
            combinedOutOfStockHeader.setTrend("Up");
            combinedOutOfStockHeader.setState("Error");
        } else {
            // current is equal to last (no change)
            combinedOutOfStockHeader.setTrend("None");
            combinedOutOfStockHeader.setState("None");
        }

        // 5: Save the populated Header + the historical data
        combinedOutOfStockHeaderDao.saveModel(combinedOutOfStockHeader);

        // NEED TO LOOP TO GET ALL SIX OF THEM
        // combinedOutOfStockPercentageDao.saveModel(combinedOutOfStockPercentages);

        combinedOutOfStockPercentageDao.saveModel(combinedOutOfStockPercentages);


        return combinedOutOfStockPercentages.toString();

        //return "sixMonthListValues: " + sixMonthListValues + "   \nsixMonthListLabels: " + sixMonthListLabels;
        // return "current and previous? " + currentQuarter + " \n\n\n " + currentQuarter.getPreviousQuarter();


        // return "{\"results\":" + combinedOutOfStockHeaderDao.getAll() + "}";

        //return "you can do it";

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
        header.put("n", totalValue);
        header.put("u", "CAD");
        header.put("trend", "None");
        header.put("valueColor", "None");
        header.put("details", new Date());

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