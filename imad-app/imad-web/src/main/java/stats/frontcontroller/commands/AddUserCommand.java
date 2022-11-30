package stats.frontcontroller.commands;

import java.io.IOException;

import javax.servlet.ServletException;

import ec.stats.jpa.model.User;

public class AddUserCommand extends FrontCommand {

	@Override
	public void process() throws ServletException, IOException {

		String username = request.getParameter("uname");
		String password = request.getParameter("psw");
		Integer role = Integer.parseInt(request.getParameter("role"));
		User user = new User(username, password, role);
		userDao.addUser(user);

		request.setAttribute("addUserMessage", "The user: " + username + " has been added successfully.");
		forward("admin");
	}
}