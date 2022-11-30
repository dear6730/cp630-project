package stats.frontcontroller.commands;

import java.io.IOException;

import javax.servlet.ServletException;

import ec.stats.jpa.model.User;

public class LoginCommand extends FrontCommand {
	
	private static final Integer ADMIN_USERS = 1;
	private static final Integer DEV_USERS = 2;
	private static final Integer GENERAL_USERS = 3;
	
    @Override
    public void process() throws ServletException, IOException {
    	
		String username = request.getParameter("uname");
		String password = request.getParameter("psw");
		User user = userDao.getUser(username, password);
		
		if (user == null) {
			request.setAttribute("loginMessage", "You have entered an invalid username or password");
			forward("login");
		} else {
			int role = user.getRole();
			if(ADMIN_USERS.equals(role)){
				forward("admin");
			} else if(DEV_USERS.equals(role)){
				forward("developer");
			} else if(GENERAL_USERS.equals(role)){
				forward("guest");
			}
		}
    }
}