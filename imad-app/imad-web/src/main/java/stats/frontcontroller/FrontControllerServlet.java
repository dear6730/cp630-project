package stats.frontcontroller;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ec.stats.jpa.ModelDao;
import ec.stats.jpa.UserDao;
import ec.stats.sb.StatsStatelessLocal;
import stats.frontcontroller.commands.FrontCommand;
import stats.frontcontroller.commands.UnknownCommand;

@WebServlet("/controller")
public class FrontControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    @EJB
    private UserDao userDao;

    @EJB
    private StatsStatelessLocal statsStatelessLocal;

    @EJB
    private ModelDao modelDao;
	
    public FrontControllerServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FrontCommand command = getCommand(request);
        command.init(getServletContext(), request, response, userDao, statsStatelessLocal, modelDao);
        command.process();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
    private FrontCommand getCommand(HttpServletRequest request) {
        try {
            Class<?> type = Class.forName(
              String.format(
                "ec.stats.frontcontroller.commands.%sCommand",
                request.getParameter("command")
              )
            );
            return (FrontCommand) type.asSubclass(FrontCommand.class).newInstance();
        } catch (Exception e) {
            return new UnknownCommand();
        }
    }
}
