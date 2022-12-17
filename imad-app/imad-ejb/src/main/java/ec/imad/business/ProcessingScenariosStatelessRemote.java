package ec.imad.business;

import javax.ejb.Remote;

@Remote
public interface ProcessingScenariosStatelessRemote {
    public void calculateTotalStockValueByCategory();
    public void calculateOverviewStockingIssues();
}
