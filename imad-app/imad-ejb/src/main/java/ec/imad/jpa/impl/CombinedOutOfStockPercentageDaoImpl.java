package ec.imad.jpa.impl;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import ec.imad.jpa.dao.CombinedOutOfStockPercentageDao;
import ec.imad.jpa.model.CombinedOutOfStockPercentage;

@Stateful
public class CombinedOutOfStockPercentageDaoImpl implements CombinedOutOfStockPercentageDao {
    private static final Logger LOGGER = Logger.getLogger(CombinedOutOfStockPercentageDaoImpl.class);

    
    @PersistenceContext(unitName="primary")
    private EntityManager entityManager;

    @Override
    public void saveModel(CombinedOutOfStockPercentage combinedOutOfStockPercentage) {
        LOGGER.info("Add " + combinedOutOfStockPercentage.toString());
        entityManager.persist(combinedOutOfStockPercentage);
    }

    @Override
    public List<CombinedOutOfStockPercentage> getAll() {
        return entityManager.createQuery("from CombinedOutOfStockPercentage", CombinedOutOfStockPercentage.class).getResultList();
    }
}