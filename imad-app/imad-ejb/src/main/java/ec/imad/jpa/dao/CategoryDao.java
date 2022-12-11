package ec.imad.jpa.dao;

import javax.ejb.Local;
import ec.imad.jpa.model.Category;

@Local
public interface CategoryDao {
    public void saveModel(Category category);
}
