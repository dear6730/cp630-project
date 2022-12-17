package ec.imad.jpa.dao;

import java.util.List;
import javax.ejb.Local;
import ec.imad.jpa.model.TotalStockValue;

@Local
public interface TotalStockValueDao {
    public void saveModel(TotalStockValue totalStockValue);
    public void saveModel(List<TotalStockValue> totalStockValues);
    public List<TotalStockValue> getAll();
}