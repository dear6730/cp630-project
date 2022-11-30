package ec.stats.sb;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

/**
 * Session Bean implementation class StatsSingleton
 */
@Singleton
@LocalBean
public class StatsSingleton implements StatsSingletonRemote, StatsSingletonLocal {
	private static final String FILE = "C:/enterprise/tmp/model/stats.bin";
	private ArrayList<Double> ds;
	private int count;
	private double min;
	private double max;
	private double mean;
	private double std;

	@EJB
	private StatsStatelessLocal statsStatelessLocal;

    /**
     * Default constructor. 
     */
    public StatsSingleton() {
		ds = new ArrayList<Double>();
		count = 0;
		min = 0;
		max = 0;
		mean = 0;
		std = 0;
    }

	/**
	* returns the number of data elements in the data set.
	*/
    @Override
    public int getCount() {
        return count;
    }

	/**
	* adds a Double data value to data set. 
	*/
    @Override
    public void addData(Double value) {
		ds.add(value);
		int currentCount = count;
	    count += 1;
	    if (count == 1) {
	        min = value;
	        max = value;
	        mean = value;
	        std = 0;
	    } else {
	        if (value < min) min = value; 
	        if (value > max) max = value;
	        
	        double currentMean = mean;
	        // Mean=Sum of all elements/total number of elements
	        mean = (currentMean * currentCount + value) / count;
	        
	        // Standard deviation is computed using the formula square root of ∑(Xi - ų)2 / N 
	        // where Xi is the element of the array, ų is mean of the elements of the array, 
	        // N is the number of elements, ∑ is the sum of the each element.
	        // source: https://www.tutorialspoint.com/java-program-to-calculate-standard-deviation#:~:text=Standard%20deviation%20is%20computed%20using,sum%20of%20the%20each%20element.
	        std = Math.sqrt(((std * std + currentMean * currentMean) * currentCount + value * value) / count - mean * mean);
	    }
	}

	/**
	* computes the descriptive statistics
	*/
    @Override
	public void stats(){
	    count = ds.size();
	    if (count == 0) {
	        min = 0;
	        max = 0;
	        mean = 0;
	        std = 0;
	    }
	    else {
	    	// add your code
	        // use one for loop to compute the stats values. 
	        // for (Double x : ds) {...}
	    	double _min = ds.get(0); // first should be the min
	    	double _max = _min;
	    	double _mean = 0;
	    	double _std = 0;
	    	
	    	for (Double value : ds) {
	    		if(value < _min) { _min = value; }
	    		if(value > _max) { _max = value; }
	    		_mean += value;
	    		_std += value * value;
	    	}
	    	// Mean=Sum of all elements/total number of elements
	        _mean = _mean / count;
	        
	        // Standard deviation is computed using the formula square root of ∑(Xi - ų)2 / N 
	        // where Xi is the element of the array, ų is mean of the elements of the array, 
	        // N is the number of elements, ∑ is the sum of the each element.
	        // source: https://www.tutorialspoint.com/java-program-to-calculate-standard-deviation#:~:text=Standard%20deviation%20is%20computed%20using,sum%20of%20the%20each%20element.
	        _std = Math.sqrt(_std / count - _mean * _mean);
	        
	        // then resets the corresponding property values.
			min = _min;
			max = _max;
			mean = _mean;
			std = _std;
	    }
	}
	
	/**
	* saves the serializable object, StatsSummary (just contains the attributes of the simple statistics), 
	* to file stats.bin in `C:/enterprise/tmp/model/` directory (need to create this directory first).
	*/
	@Override
	public void saveModel() {
		
		try {
			//process stats before save
			stats();
			
			StatsSummary sm = new StatsSummary();
			sm.setCount(count);
			sm.setMin(min);
			sm.setMax(max);
			sm.setMean(mean);
			sm.setSTD(std);
			
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(FILE));
			os.writeObject(sm);
			os.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			// notify StatsStatelessLocal to update the summary
			statsStatelessLocal.notifyModelChange();
		}
    }
}