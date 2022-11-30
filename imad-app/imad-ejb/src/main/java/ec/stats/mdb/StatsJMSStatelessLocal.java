package ec.stats.mdb;

import javax.ejb.Local;

@Local
public interface StatsJMSStatelessLocal {
    public void produce(String message);
    public void publish(String data);
}