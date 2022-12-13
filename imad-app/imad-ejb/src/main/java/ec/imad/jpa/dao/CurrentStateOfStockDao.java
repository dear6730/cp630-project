package ec.imad.jpa.dao;

import java.util.List;

import javax.ejb.Local;

import ec.imad.jpa.model.CurrentStateOfStock;

@Local
public interface CurrentStateOfStockDao {
    public void saveModel(CurrentStateOfStock currentStateOfStock);
    public List<CurrentStateOfStock> getAll();
}