package ec.imad.jpa.impl;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import ec.imad.jpa.dao.LocationDao;
import ec.imad.jpa.model.Location;

@Stateful
public class LocationDaoImpl implements LocationDao {
    private static final Logger LOGGER = Logger.getLogger(ProductDaoImpl.class);

    
    @PersistenceContext(unitName="primary")
    private EntityManager entityManager;

    @Override
    public void saveModel(Location location) {
        LOGGER.info("Add " + location.toString());
        entityManager.persist(location);
    }
}