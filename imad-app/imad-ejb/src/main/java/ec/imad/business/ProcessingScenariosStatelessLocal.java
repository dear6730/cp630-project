package ec.imad.business;

import javax.ejb.Local;

@Local
public interface ProcessingScenariosStatelessLocal {
    public void calculateTotalStockValue();
    public void calculateTotalStockValueByCategory();
    public void calculateTop5StockValueProducts();
    public void calculateOverviewStockingIssues();
    public void generateCurrentStateOfStockList();
    public void calculateCombinedPercentageHistory();
}
