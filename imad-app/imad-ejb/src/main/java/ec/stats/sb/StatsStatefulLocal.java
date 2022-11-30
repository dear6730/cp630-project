package ec.stats.sb;

import javax.ejb.Local;

@Local
public interface StatsStatefulLocal {
	public void insertData(Double value); 
	//: inserts a data into the data list of the StatsSingleton object by 
	//invoking its addData(Double) method. 

	public void createModel();
	//: saves the StatsSummary object by invoking StatsSingleton's 
	//saveModel() method.
	
	public String getStats();
	//	: gets the stats summary string by invoking the toString() 
	// of StatsStateless object. 
}