package ec.imad.jpa.impl;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import ec.imad.jpa.dao.StockDao;
import ec.imad.jpa.model.Stock;
import ec.imad.jpa.model.TotalStockCategory;

@Stateful
public class StockDaoImpl implements StockDao {
    private static final Logger LOGGER = Logger.getLogger(StockDaoImpl.class);

    @PersistenceContext(unitName="primary")
    private EntityManager entityManager;

    @Override
    public List<TotalStockCategory> getTotalStockValuePerCategory() {
    
        try {
            LOGGER.info("getTotalStockValuePerCategory ");
			// final String QUERY = "select u from Model u where u.name = :modelname";
            final String QUERY2 = "SELECT c.name AS name, SUM(p.price) AS value "
                                + "FROM "
                                +   "Stock S, "
                                +   "Product P, "
                                +   "Category C "
                                + "WHERE "
                                +    "C.id = P.category AND P.id = S.product "
                                +  "GROUP BY c.name";
            List<TotalStockCategory> result = entityManager.createQuery(QUERY2, TotalStockCategory.class).getResultList();
            return result;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Stock> getAll() {
        return (List<Stock>) entityManager.createQuery("from Stock", Stock.class).getResultList();
    }
}