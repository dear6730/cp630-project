package ec.imad.jpa.impl;

import java.util.List;
import java.util.ArrayList;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import ec.imad.jpa.dao.HistoricalStockDao;
import ec.imad.jpa.model.HistoricalStock;

import ec.imad.business.model.Quarter;

@Stateful
public class HistoricalStockDaoImpl implements HistoricalStockDao {
    private static final Logger LOGGER = Logger.getLogger(HistoricalStockDaoImpl.class);

    @PersistenceContext(unitName="primary")
    private EntityManager entityManager;

    @Override
    public List<HistoricalStock> getAll() {
        return (List<HistoricalStock>) entityManager.createQuery("from HistoricalStock", HistoricalStock.class).getResultList();
    }

    @Override
    public List<HistoricalStock> getLastTwoQuarters(Quarter currentQuarter, Quarter oneQuarterAgo, Quarter twoQuartersAgo, List<Integer> sixMonthListValues) {

        final String QUERY = "SELECT hs "
                            + "FROM "
                            +   "HistoricalStock hs "
                            + "WHERE "
                            +    "(month = " + sixMonthListValues.get(0) + " AND year = " + twoQuartersAgo.getYear() + ") "
                            +    "OR (month = " + sixMonthListValues.get(1) + " AND year = " + twoQuartersAgo.getYear() + ") "
                            +    "OR (month = " + sixMonthListValues.get(2) + " AND year = " + twoQuartersAgo.getYear() + ") "
                            +    "OR (month = " + sixMonthListValues.get(3) + " AND year = " + oneQuarterAgo.getYear() + ") "
                            +    "OR (month = " + sixMonthListValues.get(4) + " AND year = " + oneQuarterAgo.getYear() + ") "
                            +    "OR (month = " + sixMonthListValues.get(5) + " AND year = " + oneQuarterAgo.getYear() + ") ";

        return (List<HistoricalStock>) entityManager.createQuery("from HistoricalStock", HistoricalStock.class).getResultList();
        
    }

}