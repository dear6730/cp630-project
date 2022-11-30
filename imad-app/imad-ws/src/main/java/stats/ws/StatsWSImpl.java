package stats.ws;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.annotation.WebServlet;

import ec.stats.sb.StatsStatelessLocal;

@WebServlet("/StatsWSImpl")
@WebService(endpointInterface = "stats.ws.StatsWS")
public class StatsWSImpl implements StatsWS {

    @EJB
    private StatsStatelessLocal statsStatelessLocal;

    @WebMethod()
    public int getCount() {
        return statsStatelessLocal.getCount();
    }

    @WebMethod()
    public double getMax() {
        return statsStatelessLocal.getMax();
    }

    @WebMethod()
    public double getMean() {
        return statsStatelessLocal.getMean();
    }

    @WebMethod()
    public double getMin() {
        return statsStatelessLocal.getMin();
    }

    
    @WebMethod()
    public double getSTD() {
        return statsStatelessLocal.getSTD();
    }
}