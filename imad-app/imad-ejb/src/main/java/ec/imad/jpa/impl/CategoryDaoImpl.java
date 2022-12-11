package ec.imad.jpa.impl;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import ec.imad.jpa.dao.CategoryDao;
import ec.imad.jpa.model.Category;

@Stateful
public class CategoryDaoImpl implements CategoryDao {
    private static final Logger LOGGER = Logger.getLogger(CategoryDaoImpl.class);

    @PersistenceContext(unitName="primary")
    private EntityManager entityManager;

    @Override
    public void saveModel(Category category) {
        LOGGER.info("Add " + category.toString());
        entityManager.persist(category);
    }
}
