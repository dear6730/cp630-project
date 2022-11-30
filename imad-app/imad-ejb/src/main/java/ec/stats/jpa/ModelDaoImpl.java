package ec.stats.jpa;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import ec.stats.jpa.model.Model;

@Stateful
public class ModelDaoImpl implements ModelDao {
    private static final Logger LOGGER = Logger.getLogger(ModelDaoImpl.class);

    @PersistenceContext(unitName="primary")
    private EntityManager entityManager;

    @Override
    public void saveModel(Model model) {
        LOGGER.info("Add model " + model.toString());
        entityManager.persist(model);
    }

    @Override
    public Model getModel(String modelname) {
        try {
			LOGGER.info("getModel : name : " + modelname);
			final String QUERY = "select u from Model u where u.name = :modelname";
            Query query = entityManager.createQuery(QUERY);
            query.setParameter("modelname", modelname);
            return (Model) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}