package ec.imad.jpa.impl;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import ec.imad.jpa.dao.TotalStockCategoryDao;
import ec.imad.jpa.model.TotalStockCategory;

@Stateful
public class TotalStockCategoryDaoImpl implements TotalStockCategoryDao {
    private static final Logger LOGGER = Logger.getLogger(TotalStockCategoryDaoImpl.class);
    
    @PersistenceContext(unitName="primary")
    private EntityManager entityManager;

    @Override
    public void saveModel(TotalStockCategory totalStockCategory) {
        LOGGER.info("Add " + totalStockCategory.toString());
        entityManager.persist(totalStockCategory);
    }

    @Override
    public List<TotalStockCategory> getAll() {
        return entityManager.createQuery("from TotalStockCategory", TotalStockCategory.class).getResultList();
    }

    @Override
    public void saveModel(List<TotalStockCategory> totalStockCategories) {
        for (TotalStockCategory totalStockCategory : totalStockCategories) {
            saveModel(totalStockCategory);
        }
    }
}