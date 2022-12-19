package ec.imad.jpa.dao;

import java.util.List;

import javax.ejb.Local;

import ec.imad.business.model.Quarter;

import ec.imad.jpa.model.HistoricalStock;

@Local
public interface HistoricalStockDao {
    public List<HistoricalStock> getAll();
    public List<HistoricalStock> getLastTwoQuarters(Quarter currentQuarter, Quarter oneQuarterAgo, Quarter twoQuartersAgo, List<Integer> sixMonthListValues);
}
