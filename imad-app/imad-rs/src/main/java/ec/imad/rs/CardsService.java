package ec.imad.rs;

import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import ec.imad.jpa.dao.TotalStockCategoryDao;
import ec.imad.jpa.dao.Top5ProductsDao;
import ec.imad.jpa.dao.CurrentStateOfStockDao;
import ec.imad.jpa.dao.OverviewStockingIssuesDao;
import ec.imad.jpa.dao.TotalStockValueDao;

import ec.imad.jpa.model.TotalStockCategory;
import ec.imad.jpa.model.TotalStockValue;

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
    private TotalStockValueDao totalStockValueDao;

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
}