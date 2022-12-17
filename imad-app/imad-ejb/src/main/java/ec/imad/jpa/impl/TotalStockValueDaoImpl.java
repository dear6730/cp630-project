package ec.imad.jpa.impl;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import ec.imad.jpa.dao.TotalStockValueDao;
import ec.imad.jpa.model.TotalStockValue;

@Stateful
public class TotalStockValueDaoImpl implements TotalStockValueDao {
    private static final Logger LOGGER = Logger.getLogger(TotalStockValueDaoImpl.class);

    
    @PersistenceContext(unitName="primary")
    private EntityManager entityManager;

    @Override
    public void saveModel(TotalStockValue totalStockValue) {
        LOGGER.info("Add " + totalStockValue.toString());
        entityManager.persist(totalStockValue);
    }

    @Override
    public List<TotalStockValue> getAll() {
        return entityManager.createQuery("from TotalStockValue", TotalStockValue.class).getResultList();
    }
}