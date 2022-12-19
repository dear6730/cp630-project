package ec.imad.jpa.dao;

import java.util.List;

import javax.ejb.Local;

import ec.imad.jpa.model.CombinedOutOfStockPercentage;

@Local
public interface CombinedOutOfStockPercentageDao {
    public void saveModel(CombinedOutOfStockPercentage combinedOutOfStockPercentage);
    public List<CombinedOutOfStockPercentage> getAll();
    public void saveModel(List<CombinedOutOfStockPercentage> combinedOutOfStockPercentageList);
}