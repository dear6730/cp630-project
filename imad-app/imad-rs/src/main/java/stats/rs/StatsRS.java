package stats.rs;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import ec.stats.sb.StatsStatelessLocal;

@Path("/")
@RequestScoped
public class StatsRS {  
    @EJB
    private StatsStatelessLocal statsStatelessLocal;

    @GET
    @Path("/count")
    @Produces({ "application/json" })
    public String count() {
        return "{\"count\":\"" + statsStatelessLocal.getCount() + "\"}";
    }

    @GET
    @Path("/max")
    @Produces({ "application/json" })
    public String max() {
        return "{\"max\":\"" + statsStatelessLocal.getMax() + "\"}";
    }

    @GET
    @Path("/min")
    @Produces({ "application/json" })
    public String min() {
        return "{\"min\":\"" + statsStatelessLocal.getMin() + "\"}";
    }

    @GET
    @Path("/mean")
    @Produces({ "application/json" })
    public String mean() {
        return "{\"mean\":\"" + statsStatelessLocal.getMean() + "\"}";
    }

    @GET
    @Path("/std")
    @Produces({ "application/json" })
    public String std() {
        return "{\"std\":\"" + statsStatelessLocal.getSTD() + "\"}";
    }
}