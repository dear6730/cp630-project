package ec.imad.jpa.dao;

import java.util.List;

import javax.ejb.Local;

import ec.imad.jpa.model.Stock;
import ec.imad.jpa.model.TotalStockCategory;

@Local
public interface StockDao {
    public List<TotalStockCategory> getTotalStockValuePerCategory();
    public List<Stock> getAll();
}
