package stats.frontcontroller.commands;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ec.stats.jpa.ModelDao;
import ec.stats.jpa.UserDao;
import ec.stats.sb.StatsStatelessLocal;

import java.io.IOException;

public abstract class FrontCommand {
    protected ServletContext context;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected UserDao userDao;
    protected StatsStatelessLocal statsStatelessLocal;
    protected ModelDao modelDao;

    public void init(
      ServletContext servletContext,
      HttpServletRequest servletRequest,
      HttpServletResponse servletResponse,
      UserDao userDao,
      StatsStatelessLocal statsStatelessLocal,
      ModelDao modelDao
    ) {
        this.context = servletContext;
        this.request = servletRequest;
        this.response = servletResponse;
        this.userDao = userDao;
        this.statsStatelessLocal = statsStatelessLocal;
        this.modelDao = modelDao;
    }

    public abstract void process() throws ServletException, IOException;

    protected void forward(String target) throws ServletException, IOException {
        target = String.format("/WEB-INF/jsp/%s.jsp", target);
        RequestDispatcher dispatcher = context.getRequestDispatcher(target);
        dispatcher.forward(request, response);
    }
    
    protected void redirect(String page) throws ServletException, IOException {
    	response.sendRedirect(page);
    }
}