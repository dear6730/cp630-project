package ec.stats.sb;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

/**
 * Session Bean implementation class StatsStateless
 */
@Stateless
@LocalBean
public class StatsStateless implements StatsStatelessRemote, StatsStatelessLocal {
    private static final Logger LOGGER = Logger.getLogger(StatsStateless.class);
	private static final String FILE = "C:/enterprise/tmp/model/stats.bin";
	private StatsSummary sm = null;
    
    @EJB
    private StatsSingleton statsSingleton;

    public StatsStateless() {
    }
	
	public void loadModel(){
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(FILE));
			sm = (StatsSummary) is.readObject();
			is.close();
		} catch (FileNotFoundException e2) {
			LOGGER.error("FileNotFoundException", e2);
		} catch (IOException e1) {
			LOGGER.error("IOException", e1);
		} catch (ClassNotFoundException e) {
			LOGGER.error("ClassNotFoundException", e);
		}
	}
	
	@Override
	public void notifyModelChange() {
		LOGGER.info("The model has been changed, re-load model");
		loadModel();
	}

    @Override
	public int getCount() {
		LOGGER.info("getCount method is called");
		if (sm == null) { loadModel();}
		if (sm != null)
			return sm.getCount();
		else 
			return 0;
	}
	
	//: gets the minimum of the data set. 
	@Override
	public double getMin() {
		LOGGER.info("getMin method is called");
		if (sm == null) { loadModel();}
		if (sm != null)
			return sm.getMin();
		else 
			return 0;
	}	
	
	//: gets maximum of the data set.
	@Override
	public double getMax() {
		LOGGER.info("getMax method is called");
		if (sm == null) { loadModel();}
		if (sm != null)
			return sm.getMax();
		else 
			return 0;
	}		

	//: gets the average.
	@Override	
	public double getMean() {
		LOGGER.info("getMean method is called");
		if (sm == null) { loadModel();}
		if (sm != null)
			return sm.getMean();
		else 
			return 0;
	}			
	
	//: gets the standard deviation.
	@Override	
	public double getSTD() {
		LOGGER.info("getSTD method is called");
		if (sm == null) { loadModel();}
		if (sm != null)
			return sm.getSTD();
		else 
			return 0;
	}				

	//: returns a String of the simple statssummary.
	@Override	
	public String toString() {
		LOGGER.info("toString method is called");
		if (sm == null) { loadModel();}
		if (sm != null)
			return sm.toString();
		else 
			return null;
	}					
}