package stats.frontcontroller.commands;

import java.io.IOException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import org.apache.commons.lang3.SerializationUtils;
import ec.stats.jpa.model.Model;
import ec.stats.sb.StatsSummary;

public class SaveModelCommand extends FrontCommand {

	@Override
	public void process() throws ServletException, IOException {

		String modelname = request.getParameter("value");
            
		StatsSummary statsSummary = new StatsSummary();
		statsSummary.setCount(statsStatelessLocal.getCount());
		statsSummary.setMin(statsStatelessLocal.getMin());
		statsSummary.setMax(statsStatelessLocal.getMax());
		statsSummary.setMean(statsStatelessLocal.getMean());
		statsSummary.setSTD(statsStatelessLocal.getSTD());

		Model model = new Model();
		model.setName(modelname);
		model.setClassname(statsSummary.getClass().getName());
		model.setObject(SerializationUtils.serialize(statsSummary));
		model.setDate(new Timestamp(System.currentTimeMillis()));
		modelDao.saveModel(model);

		request.setAttribute("addModelMessage", "The model name: " + modelname + " has been added successfully.");
		forward("developer");
	}
}