package ec.stats.sb;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;

@Stateful
@LocalBean
public class StatsStateful implements StatsStatefulRemote, StatsStatefulLocal {
        
    @EJB
    private StatsStatelessLocal statsStatelessLocal;
    
    @EJB
    private StatsSingletonLocal statsSingletonLocal;

    @Override
    public void insertData(Double a) {
        statsSingletonLocal.addData(a);
    }

    @Override
    public void createModel() {
        statsSingletonLocal.saveModel();
    }

    @Override
    public String getStats() {
        return statsStatelessLocal.toString();
    }
}