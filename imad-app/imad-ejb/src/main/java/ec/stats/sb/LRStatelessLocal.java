package ec.stats.sb;

import javax.ejb.Local;

@Local
public interface LRStatelessLocal {
	public double prediction(String modelName, double [] arrayUnknownParams);
}