package ec.stats.sb;

import javax.ejb.Remote;

@Remote
public interface StatsSingletonRemote {
	public void addData(Double value);
	public int getCount();
	public void stats();
	public void saveModel();
}