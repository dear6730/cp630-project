package ec.imad.jpa.impl;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import ec.imad.jpa.dao.Top5ProductsDao;
import ec.imad.jpa.model.Top5Products;

@Stateful
public class Top5ProductsDaoImpl implements Top5ProductsDao {
    private static final Logger LOGGER = Logger.getLogger(Top5ProductsDaoImpl.class);

    
    @PersistenceContext(unitName="primary")
    private EntityManager entityManager;

    @Override
    public void saveModel(Top5Products top5Products) {
        LOGGER.info("Add " + top5Products.toString());
        entityManager.persist(top5Products);
    }

    @Override
    public List<Top5Products> getAll() {
        return entityManager.createQuery("from Top5Products").getResultList();
    }
}