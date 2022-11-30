package ec.stats.jpa;

import javax.ejb.Local;
import ec.stats.jpa.model.Model;

@Local
public interface ModelDao {
	public void saveModel(Model model);
	public Model getModel(String modelname); // get model by name  
}