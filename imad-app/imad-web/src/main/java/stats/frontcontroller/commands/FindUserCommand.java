package stats.frontcontroller.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import ec.stats.jpa.model.User;

public class FindUserCommand extends FrontCommand {

	@Override
	public void process() throws ServletException, IOException {
		String username = request.getParameter("uname");
		User user = userDao.getUser(username);
		
		if(user == null){
			request.setAttribute("userNotFound", "User not found.");
		} else { 
			List<User> users = new ArrayList<User>();
			users.add(user);
			request.setAttribute("users", users);
		}
		forward("admin");
	}
}