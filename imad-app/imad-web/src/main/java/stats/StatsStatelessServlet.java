package stats;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ec.stats.sb.StatsStatelessLocal;

@WebServlet("/StatsStatelessServlet")
public class StatsStatelessServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @EJB
    private StatsStatelessLocal statsStatelessLocal;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
			String message = statsStatelessLocal.toString();
			out.println((new Date()).toString()+"<br><br>");
			if(message != null) {
				message = "Summary: " + message;
			} else {			
				message = "There is no information saved.";
			}
			out.println(message);
			out.println("<br><br>");
			out.println("<button onclick=\"history.back()\">Go Back</button>");
        } catch (Exception ex) {
            throw new ServletException(ex);
        } finally {
            out.close();
        }
    }
}