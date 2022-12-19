package ec.imad.jpa.impl;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import ec.imad.jpa.dao.OverviewStockingIssuesDao;
import ec.imad.jpa.model.OverviewStockingIssues;

@Stateful
public class OverviewStockingIssuesDaoImpl implements OverviewStockingIssuesDao {
    private static final Logger LOGGER = Logger.getLogger(OverviewStockingIssuesDaoImpl.class);

    
    @PersistenceContext(unitName="primary")
    private EntityManager entityManager;

    @Override
    public void saveModel(OverviewStockingIssues overviewStockingIssues) {
        LOGGER.info("Add " + overviewStockingIssues.toString());
        entityManager.persist(overviewStockingIssues);
    }

    @Override
    public List<OverviewStockingIssues> getAll() {
        return entityManager.createQuery("from OverviewStockingIssues", OverviewStockingIssues.class).getResultList();
    }
}