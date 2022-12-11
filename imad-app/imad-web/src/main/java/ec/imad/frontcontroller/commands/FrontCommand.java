package ec.imad.frontcontroller.commands;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ec.imad.jpa.dao.CategoryDao;
import ec.imad.jpa.dao.LocationDao;
import ec.imad.jpa.dao.ProductDao;

public abstract class FrontCommand {
    protected ServletContext context;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    protected CategoryDao categoryDao;
    protected ProductDao productDao;
    protected LocationDao locationDao;

    public void init(
      ServletContext servletContext,
      HttpServletRequest servletRequest,
      HttpServletResponse servletResponse,
      CategoryDao categoryDao,
      ProductDao productDao,
      LocationDao locationDao
    ) {
        this.context = servletContext;
        this.request = servletRequest;
        this.response = servletResponse;
        this.categoryDao = categoryDao;
        this.productDao = productDao;
        this.locationDao = locationDao;
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