package ec.imad.frontcontroller;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ec.imad.frontcontroller.commands.FrontCommand;
import ec.imad.jpa.dao.CategoryDao;
import ec.imad.jpa.dao.LocationDao;
import ec.imad.jpa.dao.ProductDao;

@WebServlet("/controller2")
public class FrontControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    @EJB
    private CategoryDao categoryDao;
    
    @EJB
    private ProductDao productDao;

    @EJB
    private LocationDao locationDao;

    public FrontControllerServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FrontCommand command = getCommand(request);
        command.init(getServletContext(), request, response, categoryDao, productDao, locationDao);
        command.process();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
    private FrontCommand getCommand(HttpServletRequest request) {
        try {
            Class<?> type = Class.forName(
              String.format(
                "ec.imad.frontcontroller.commands.%sCommand",
                request.getParameter("command")
              )
            );
            return (FrontCommand) type.asSubclass(FrontCommand.class).newInstance();
        } catch (Exception e) {
            // return new UnknownCommand();
            return null;
        }
    }
}
