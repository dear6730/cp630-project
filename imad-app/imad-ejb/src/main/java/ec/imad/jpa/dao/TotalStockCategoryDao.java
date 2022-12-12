package ec.imad.jpa.dao;

import java.util.List;

import javax.ejb.Local;

import ec.imad.jpa.model.TotalStockCategory;

@Local
public interface TotalStockCategoryDao {
    public void saveModel(TotalStockCategory totalStockCategory);
    public List<TotalStockCategory> getAll();
}