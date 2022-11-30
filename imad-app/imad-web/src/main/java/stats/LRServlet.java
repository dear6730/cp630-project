package stats;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.StringTokenizer;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ec.stats.sb.LRStatelessLocal;

@WebServlet("/LRServlet")
public class LRServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private LRStatelessLocal lrStatelessLocal;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            // capture HTML params
            String modelName = request.getParameter("modelName");
            String unknownParams = request.getParameter("unknownParams");
            String message = null;

            if(!modelName.equals("") && !unknownParams.equals("")){
                double [] arrayUnknownParams = this.convertUnknowParamsToDoubleArray(unknownParams);
                double prediction = lrStatelessLocal.prediction(modelName, arrayUnknownParams);
                message = "Prediction: " + prediction;
            } else {			
				message = "Please provide model name and unknown parameters separated by comma.";
			}

			out.println((new Date()).toString()+"<br><br>");
			out.println(message);
			out.println("<br><br>");
			out.println("<button onclick=\"history.back()\">Go Back</button>");
        } catch (Exception ex) {
            throw new ServletException(ex);
        } finally {
            out.close();
        }
    }

    private double [] convertUnknowParamsToDoubleArray(String in) {
        // Convert all parameters to array of double to use the DenseInstance API
        StringTokenizer st = new StringTokenizer(in, ",");
        int index = 0;
        double [] out = new double[st.countTokens()];
        while(st.hasMoreTokens()){
            out[index] = Double.parseDouble(st.nextToken());
            index++;
        }
        return out;
    }
}