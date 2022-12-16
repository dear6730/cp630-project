package ec.imad.jpa.dao;

import java.util.List;

import javax.ejb.Local;

import ec.imad.jpa.model.OverviewStockingIssues;

@Local
public interface OverviewStockingIssuesDao {
    public void saveModel(OverviewStockingIssues overviewStockingIssues);
    public List<OverviewStockingIssues> getAll();
}