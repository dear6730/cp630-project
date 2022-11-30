package stats.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface StatsWS {
    @WebMethod
    public int getCount();
    @WebMethod
    public double getMin();
    @WebMethod
    public double getMax();
    @WebMethod
    public double getMean();
    @WebMethod
    public double getSTD();
}