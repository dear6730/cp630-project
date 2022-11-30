package stats.frontcontroller.commands;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import ec.stats.jpa.model.User;

public class ListAllUsersCommand extends FrontCommand {

	@Override
	public void process() throws ServletException, IOException {
		List<User> users = userDao.getAllUser();
		request.setAttribute("users", users);
		forward("admin");
	}
}