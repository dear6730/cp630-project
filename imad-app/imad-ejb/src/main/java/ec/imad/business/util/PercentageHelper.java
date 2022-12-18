package ec.imad.business.util;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.imad.jpa.dao.StockDao;
import ec.imad.jpa.dao.ProductDao;
import ec.imad.jpa.dao.HistoricalStockDao;

import ec.imad.jpa.model.Stock;
import ec.imad.jpa.model.Product;
import ec.imad.jpa.model.HistoricalStock;

public class PercentageHelper {

        private Map<Integer, Integer> stockMap;
        private List<Stock> allStock;
        private List<Integer> productIds;

        private Map<Integer, Integer> productMap;
        private List<Product> allProduct;
        private List<Integer> globalProductIds;

        private int countOfAllProductsCarriedGlobally = 0;
        private int countOfAllProductsOutOfStock = 0;
        private int countOfAllProductsNearlyOutOfStock = 0;

        private BigDecimal percentageOutOfStock = null;
        private BigDecimal percentageNearlyOutOfStock = null;

    public PercentageHelper(ProductDao productDao, StockDao stockDao) {
            // consider initializing with the stuff in calculateOverviewStockingIssues() in ProcessingScenariosStateless

            // then use those lists to calculate the various counts, percentages, etc.

            stockMap = new HashMap<Integer, Integer>();
            productIds = new ArrayList<Integer>();

            productMap = new HashMap<Integer, Integer>();
            globalProductIds = new ArrayList<Integer>();

            init(productDao, stockDao);
    }

    private void init(ProductDao productDao, StockDao stockDao) {

        allStock = stockDao.getAll();
        allProduct = productDao.getAll();

        //get all products (globally)
        countOfAllProductsCarriedGlobally = allProduct.size();

        // get all products & global reorder point
        for(Product product : allProduct) {
            Integer productId = product.getId();
            globalProductIds.add(productId);
            productMap.put(productId, product.getGlobalReorderPoint());
        }

        //get all stock & quantity
        for (Stock stock : allStock) {
            Integer productId = stock.getProduct().getId();
            productIds.add(productId);
            Integer quantity = stockMap.containsKey(productId) ? stockMap.get(productId) : 0;
            quantity += stock.getQuantity();
            stockMap.put(productId, quantity);
        }

        //count all products that are not in stock table
        globalProductIds.removeAll(productIds);
        countOfAllProductsOutOfStock += globalProductIds.size();

        //count all products with global stock count of zero (in stock table)
        countOfAllProductsOutOfStock += stockMap.values().stream().filter(v -> v == 0).count();

        //count all products with global stock count <= global_reorder_point (in stock table)
        for (Integer productId : stockMap.keySet()) {
            Integer quantity = stockMap.get(productId);
            Integer globalReorderPoint = productMap.get(productId);
            if(quantity > 0 && quantity <= globalReorderPoint) {
                countOfAllProductsNearlyOutOfStock += 1;
            }
        }

        // READY TO WRITE countOfAllProductsOutOfStock and countOfAllProductsNearlyOutOfStock to A table


    }

    public BigDecimal calculatePercentageOutOfStock() {
        percentageOutOfStock = new BigDecimal(countOfAllProductsOutOfStock/(double)countOfAllProductsCarriedGlobally*100.0);
        percentageOutOfStock = percentageOutOfStock.setScale(2, RoundingMode.HALF_EVEN);

        return percentageOutOfStock;
    }

    public BigDecimal calculatePercentageNearlyOutOfStock() {
        percentageNearlyOutOfStock = new BigDecimal(countOfAllProductsNearlyOutOfStock/(double)countOfAllProductsCarriedGlobally*100.0);
        percentageNearlyOutOfStock = percentageNearlyOutOfStock.setScale(2, RoundingMode.HALF_EVEN);

        return percentageNearlyOutOfStock;
    }

    public BigDecimal calculateCombinedPercentage() {

        if(percentageOutOfStock == null)
            calculatePercentageOutOfStock();

        if(percentageNearlyOutOfStock == null)
            calculatePercentageNearlyOutOfStock();

        return percentageOutOfStock.add(percentageNearlyOutOfStock);
    }

    public BigDecimal calculateCombinedPercentageHistorical() {

        return new BigDecimal(-1);
    }

    
}