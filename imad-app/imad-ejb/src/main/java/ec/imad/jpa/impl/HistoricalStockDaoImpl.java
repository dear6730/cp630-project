package ec.imad.jpa.impl;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.imad.business.model.Quarter;
import ec.imad.jpa.dao.HistoricalStockDao;
import ec.imad.jpa.model.HistoricalStock;

@Stateful
public class HistoricalStockDaoImpl implements HistoricalStockDao {

    @PersistenceContext(unitName="primary")
    private EntityManager entityManager;

    @Override
    public List<HistoricalStock> getAll() {
        return (List<HistoricalStock>) entityManager.createQuery("from HistoricalStock", HistoricalStock.class).getResultList();
    }

    @Override
    public List<HistoricalStock> getLastTwoQuarters(Quarter currentQuarter, Quarter oneQuarterAgo, Quarter twoQuartersAgo, List<Integer> sixMonthListValues) {
        return (List<HistoricalStock>) entityManager.createQuery("from HistoricalStock", HistoricalStock.class).getResultList();
    }
}