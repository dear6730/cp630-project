package ec.imad.jpa.dao;

import java.util.List;

import javax.ejb.Local;
import ec.imad.jpa.model.Product;

@Local
public interface ProductDao {
    public void saveModel(Product product);
    public List<Product> getAll();
}
