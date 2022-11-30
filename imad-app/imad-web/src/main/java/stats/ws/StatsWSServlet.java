package stats.ws;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

@WebServlet("/statsws")
public class StatsWSServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        URL url = new URL("http://localhost:8080/stats-ws/StatsWSImpl?wsdl");
        QName qname = new QName("http://ws.stats/", "StatsWSImplService");
        Service service = Service.create(url, qname);
        StatsWS statsWS = service.getPort(StatsWS.class);
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
            out.println("<title>A3 - Q1.2 SOAP WS Clients</title></head>");
            out.println("<body><h1>Summary of StatsWS: </h1>");
            out.println("<h3>getCount().: " + statsWS.getCount() + "</h3>");
            out.println("<h3>getMin()...: " + statsWS.getMin() + "</h3>");
            out.println("<h3>getMax()...: " + statsWS.getMax() + "</h3>");
            out.println("<h3>getMean()..: " + statsWS.getMean() + "</h3>");
            out.println("<h3>getSTD()...: " + statsWS.getSTD() + "</h3>");
            out.println("</body></html>");
        } catch (Exception ex) {
            throw new ServletException(ex);
        } finally {
            out.close();
        }
    }
}