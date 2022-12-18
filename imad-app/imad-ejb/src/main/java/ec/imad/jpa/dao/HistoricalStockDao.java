package ec.imad.jpa.dao;

import java.util.List;

import javax.ejb.Local;

import ec.imad.jpa.model.HistoricalStock;
// import ec.imad.jpa.model.TotalStockCategory;

@Local
public interface HistoricalStockDao {
    // public List<TotalStockCategory> getTotalStockValuePerCategory();
    public List<HistoricalStock> getAll();
}
