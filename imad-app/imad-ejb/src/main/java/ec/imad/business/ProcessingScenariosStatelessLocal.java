package ec.imad.business;

import javax.ejb.Local;

@Local
public interface ProcessingScenariosStatelessLocal {
    public void calculateTotalStockValueByCategory();
    public void calculateOverviewStockingIssues();
}
