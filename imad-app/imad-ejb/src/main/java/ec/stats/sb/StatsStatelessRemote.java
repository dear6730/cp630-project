package ec.stats.sb;

import javax.ejb.Remote;

@Remote
public interface StatsStatelessRemote {
	public int getCount();		//: returns the data count.
	public double getMin();		//: gets the minimum of the data set. 
	public double getMax();		//: gets maximum of the data set.
	public double getMean(); 	//: gets the average.
	public double getSTD();		//: gets the standard deviation.
	public String toString(); 	//: returns a String of the simple statssummary.
}