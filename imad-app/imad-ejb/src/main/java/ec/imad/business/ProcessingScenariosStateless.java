package ec.imad.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import ec.imad.jpa.dao.StockDao;
import ec.imad.jpa.dao.TotalStockCategoryDao;
import ec.imad.jpa.model.Stock;
import ec.imad.jpa.model.TotalStockCategory;

@Stateless
@LocalBean
public class ProcessingScenariosStateless 
    implements ProcessingScenariosStatelessLocal, 
                    ProcessingScenariosStatelessRemote {
    private static final Logger LOGGER = Logger.getLogger(ProcessingScenariosStateless.class);

    @PersistenceContext(unitName="primary")
    private EntityManager entityManager;

    @EJB
    private StockDao stockDao;

    @EJB
    private TotalStockCategoryDao totalStockCategoryDao;

    /**
     * This method should calculate the value in stock per category (price * quantity).
     * The operational information from transactional database should 
     * be extracted, transformed and load into the analytical database.
     */
    @Override
    public void calculateTotalStockValueByCategory() {
        LOGGER.info("Start process calculateTotalStockValueByCategory");
        Map<String, BigDecimal> hm = new HashMap<String, BigDecimal>();
        List<Stock> allStock = stockDao.getAll();

        //get all categories
        for (Stock stock : allStock) {
            String catName = stock.getProduct().getCategory().getName();
            hm.put(catName, new BigDecimal(0));
        }

        // process stock value per category
        for (Stock stock : allStock) {
            String catName = stock.getProduct().getCategory().getName();
            if(hm.containsKey(catName)){
                BigDecimal totalStockValue = 
                    stock.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(stock.getQuantity()));
                BigDecimal currentValue = hm.get(catName);
                totalStockValue = totalStockValue.add(currentValue);
                hm.put(catName, totalStockValue);
            }
        }

        // assign to model 
        List<TotalStockCategory> totalStockCategories = new ArrayList<TotalStockCategory>();
        hm.entrySet().forEach(entry -> {
            TotalStockCategory tsc = new TotalStockCategory(entry.getKey(), entry.getValue());
            totalStockCategories.add(tsc);
            System.out.println(entry.getKey() + " " + entry.getValue());
        });

        // save at A table
        totalStockCategoryDao.saveModel(totalStockCategories);
        LOGGER.info("Finish process calculateTotalStockValueByCategory. Data saved at TotalStockCategory.");
    }
}