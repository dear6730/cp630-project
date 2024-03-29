package ec.imad.jpa.impl;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import ec.imad.jpa.dao.CombinedOutOfStockHeaderDao;
import ec.imad.jpa.model.CombinedOutOfStockHeader;

@Stateful
public class CombinedOutOfStockHeaderDaoImpl implements CombinedOutOfStockHeaderDao {
    private static final Logger LOGGER = Logger.getLogger(CombinedOutOfStockHeaderDaoImpl.class);

    
    @PersistenceContext(unitName="primary")
    private EntityManager entityManager;

    @Override
    public void saveModel(CombinedOutOfStockHeader combinedOutOfStockHeader) {
        LOGGER.info("Add " + combinedOutOfStockHeader.toString());
        entityManager.persist(combinedOutOfStockHeader);
    }

    @Override
    public List<CombinedOutOfStockHeader> getAll() {
        return entityManager.createQuery("from CombinedOutOfStockHeader", CombinedOutOfStockHeader.class).getResultList();
    }
}