package ec.imad.business;

import javax.ejb.Remote;

@Remote
public interface ProcessingScenariosStatelessRemote {
    public void calculateTotalStockValue();
    public void calculateTotalStockValueByCategory();
    public void calculateTop5StockValueProducts();
    public void calculateOverviewStockingIssues();
    public void generateCurrentStateOfStockList();
    public void calculateCombinedPercentageHistory();
}
