package stats.frontcontroller.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import ec.stats.jpa.model.Model;

public class FindModelCommand extends FrontCommand {

	@Override
	public void process() throws ServletException, IOException {
		String modelname = request.getParameter("modelname");
		Model model = modelDao.getModel(modelname);
		
		if(model == null){
			request.setAttribute("modelNotFound", "Model not found.");
		} else { 
			List<Model> models = new ArrayList<Model>();
			models.add(model);
			request.setAttribute("models", models);
		}
		forward("guest");
	}
}