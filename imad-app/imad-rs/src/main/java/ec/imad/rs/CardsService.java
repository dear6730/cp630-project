package ec.imad.rs;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ec.imad.jpa.dao.TotalStockCategoryDao;
import ec.imad.jpa.dao.CurrentStateOfStockDao;


@Path("/")
@RequestScoped
public class CardsService {

    @EJB
    private TotalStockCategoryDao totalStockCategoryDao;

    @EJB
    private CurrentStateOfStockDao currentStateOfStockDao;

    
    @GET
    @Path("/card2")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTotalStockValueByCategory() {
        return "{\"card2\":" + totalStockCategoryDao.getAll() + "}";
    }


    @GET
    @Path("/card5")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCurrentStateOfStock() {
        return "{\"card5\":" + currentStateOfStockDao.getAll() + "}";
    }
}