package ec.imad.jpa.impl;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import ec.imad.jpa.dao.ProductDao;
import ec.imad.jpa.model.Product;
import ec.imad.jpa.model.Top5Products;

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

    @Override
    public List<Product> getAll() {
        return (List<Product>) entityManager.createQuery("from Product", Product.class).getResultList();
    }

    @Override
    public List<Top5Products> getTop5StockValueProducts() {
        final String QUERY = 
            "SELECT NEW ec.imad.jpa.model.Top5Products(p.name AS name, p.price * SUM(s.quantity) AS value) "
            + "FROM "
            +   "Stock S, "
            +   "Product P "
            + "WHERE P.id = S.product "
            + "GROUP BY P.id "
            + "ORDER BY value DESC";
        List<Top5Products> result = entityManager.createQuery(QUERY, Top5Products.class)
                                             .setMaxResults(5)
                                             .getResultList();
        return result;
    }
}