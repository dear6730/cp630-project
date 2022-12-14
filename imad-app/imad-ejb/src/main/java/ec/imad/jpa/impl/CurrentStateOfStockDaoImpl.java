package ec.imad.jpa.impl;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import ec.imad.jpa.dao.CurrentStateOfStockDao;
import ec.imad.jpa.model.CurrentStateOfStock;

@Stateful
public class CurrentStateOfStockDaoImpl implements CurrentStateOfStockDao {
    private static final Logger LOGGER = Logger.getLogger(CurrentStateOfStockDaoImpl.class);

    
    @PersistenceContext(unitName="primary")
    private EntityManager entityManager;

    @Override
    public void saveModel(CurrentStateOfStock currentStateOfStock) {
        LOGGER.info("Add " + currentStateOfStock.toString());
        entityManager.persist(currentStateOfStock);
    }

    @Override
    public List<CurrentStateOfStock> getAll() {
        return entityManager.createQuery("from CurrentStateOfStock").getResultList();
    }
}