package ec.imad.rs;

import java.math.BigDecimal;
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
import ec.imad.jpa.dao.CurrentStateOfStockDao;
import ec.imad.jpa.dao.StockDao;
import ec.imad.jpa.dao.TotalStockCategoryDao;
import ec.imad.jpa.dao.TotalStockValueDao;
import ec.imad.jpa.model.Stock;
import ec.imad.jpa.model.TotalStockCategory;
import ec.imad.jpa.model.TotalStockValue;

@Path("/")
@RequestScoped
public class CardsService {

    @EJB
    private TotalStockCategoryDao totalStockCategoryDao;

    @EJB
    private CurrentStateOfStockDao currentStateOfStockDao;

    @EJB
    private TotalStockValueDao totalStockValueDao;

    @EJB
    private StockDao stockDao;

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
    @Path("/card5")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCurrentStateOfStock() {
        return "{\"card5\":" + currentStateOfStockDao.getAll() + "}";
    }
}