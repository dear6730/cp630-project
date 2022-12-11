package ec.imad.jpa.impl;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import ec.imad.jpa.dao.ProductDao;
import ec.imad.jpa.model.Product;

@Stateful
public class ProductDaoImpl implements ProductDao {
    private static final Logger LOGGER = Logger.getLogger(ProductDaoImpl.class);

    
    @PersistenceContext(unitName="primary")
    private EntityManager entityManager;

    @Override
    public void saveModel(Product product) {
        LOGGER.info("Add " + product.toString());
        entityManager.persist(product);
    }
}