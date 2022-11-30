package ec.stats.sb;

import javax.ejb.Local;

@Local
public interface StatsSingletonLocal {
	public void addData(Double value);
	public int getCount();
	public void stats();
	public void saveModel();
}