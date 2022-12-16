package ec.imad.jpa.dao;

import java.util.List;

import javax.ejb.Local;

import ec.imad.jpa.model.CombinedOutOfStockHeader;

@Local
public interface CombinedOutOfStockHeaderDao {
    public void saveModel(CombinedOutOfStockHeader top5Products);
    public List<CombinedOutOfStockHeader> getAll();
}