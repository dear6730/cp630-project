package ec.stats.jpa;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import ec.stats.jpa.model.User;


@Stateful
public class UserDaoImpl implements UserDao {
	private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);
	
	@PersistenceContext(unitName="primary")
    private EntityManager entityManager;

	@Override
	public void addUser(User user){
		LOGGER.info("Add user " + user.getName());
		entityManager.persist(user);
	}
	
	@Override
	public User getUser(String name, String password){
        try {
			LOGGER.info("getUser. name: " + name + " - psw : " + password);
			final String QUERY = "select u from User u where u.name = :name AND u.password = :password";
            Query query = entityManager.createQuery(QUERY);
            query.setParameter("name", name);
			query.setParameter("password", password);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
	}

	@Override
	public User getUser(String name){
        try {
			LOGGER.info("getUser. name: " + name);
			final String QUERY = "select u from User u where u.name = :name";
            Query query = entityManager.createQuery(QUERY);
            query.setParameter("name", name);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
	}
	
	@Override
	public List<User> getAllUser(){
		return entityManager.createQuery("from User").getResultList();
	}
}