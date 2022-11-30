package stats.jms;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/JMSProducerSave")
public class StatsJMSProducerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
        @Inject
        JMSContext context;
        
        @Resource(lookup = "java:/queue/test")
        private Queue queue;

        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
    
        try {
			String value = request.getParameter("value");
            final Destination destination = queue;
            context.createProducer().send(destination, value);       
			
			out.println((new Date()).toString()+"<br><br>");
            out.println("Message sent to queue/test: " + value);
			out.println("<br><br>");
			out.println("<button onclick=\"history.back()\">Go Back</button>");
        } catch (Exception ex) {
            throw new ServletException(ex);
        } finally {
            out.close();
        }
    }
}