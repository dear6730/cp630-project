package stats.jms;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ec.stats.mdb.StatsJMSStatelessLocal;

@WebServlet("/statsJMSStatelessPublisher")
public class StatsJMSStatelessPublisherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    StatsJMSStatelessLocal sb;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
    
        try {
			String data = request.getParameter("value");
			sb.publish(data);  			
			
			out.println((new Date()).toString()+"<br><br>");
            out.println("Message sent to JMS topic/test: " + data);
			out.println("<br><br>");
			out.println("<button onclick=\"history.back()\">Go Back</button>");
        } catch (Exception ex) {
            throw new ServletException(ex);
        } finally {
            out.close();
        }
    }
}