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
import ec.imad.jpa.model.CombinedOutOfStockPercentage;

import ec.imad.business.util.PercentageHelper;

import ec.imad.business.model.Quarter;

public class PercentageHelper {

    private List<Stock> allStock;
    private Map<Integer, Integer> stockMap;
    
    private List<HistoricalStock> allHistoricalStock;
    private Map<Integer, Integer> historicalStockMap;
    private Map<Integer, HashMap> monthlyMaps;
    private Map<Integer, ArrayList> monthlyProductIds;


    private List<Integer> productIds;
    private Map<Integer, Integer> productMap;
    private List<Product> allProduct;
    private List<Integer> globalProductIds;

    private int countOfAllProductsCarriedGlobally = 0;
    private int countOfAllProductsOutOfStock = 0;
    private int countOfAllProductsNearlyOutOfStock = 0;

    private BigDecimal percentageOutOfStock = null;
    private BigDecimal percentageNearlyOutOfStock = null;

    // for current 
    public PercentageHelper(ProductDao productDao, StockDao stockDao) {
        stockMap = new HashMap<Integer, Integer>();
        productIds = new ArrayList<Integer>();

        productMap = new HashMap<Integer, Integer>();
        globalProductIds = new ArrayList<Integer>();

        initialize(productDao, stockDao);
    }

    // for historical
    public PercentageHelper(Quarter quarter, ProductDao productDao, HistoricalStockDao historicalStockDao, Quarter oneQuarterAgo, Quarter twoQuartersAgo, List<Integer> sixMonthListValues) {
        historicalStockMap = new HashMap<Integer, Integer>();
        monthlyMaps = new HashMap<Integer, HashMap>();
        monthlyProductIds = new HashMap<Integer, ArrayList>();

        productMap = new HashMap<Integer, Integer>();
        globalProductIds = new ArrayList<Integer>();

        initializeHistoricalData(quarter, productDao, historicalStockDao, oneQuarterAgo, twoQuartersAgo, sixMonthListValues);
    }

    private void initialize(ProductDao productDao, StockDao stockDao) {

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

    private void initializeHistoricalData(Quarter quarter, ProductDao productDao, HistoricalStockDao historicalStockDao, Quarter oneQuarterAgo, Quarter twoQuartersAgo, List<Integer> sixMonthListValues) {

        allHistoricalStock = historicalStockDao.getLastTwoQuarters(quarter, oneQuarterAgo, twoQuartersAgo, sixMonthListValues);
        allProduct = productDao.getAll();

        //get all products (globally)
        countOfAllProductsCarriedGlobally = allProduct.size();

        // get all products & global reorder point
        for(Product product : allProduct) {
            Integer productId = product.getId();
            globalProductIds.add(productId);
            productMap.put(productId, product.getGlobalReorderPoint());
        }

        // prepare the maps of maps and lists
        for(Integer month : sixMonthListValues) {
            monthlyMaps.put(month, new HashMap<Integer, Integer>());
            monthlyProductIds.put(month, new ArrayList<Integer>());
        }

        // populate the monthly stock maps
        for(HistoricalStock stock : allHistoricalStock) {

            Integer productId = stock.getProduct().getId();
            monthlyProductIds.get(stock.getMonth()).add(productId);
            Integer quantity = historicalStockMap.containsKey(productId) ? historicalStockMap.get(productId) : 0;
            quantity += stock.getQuantity();

            monthlyMaps.get(stock.getMonth()).put(productId, quantity);
        }

        // now ready for processing/calculating
    }

    public List<CombinedOutOfStockPercentage> calculateAllHistoricalPercentages(List<Integer> sixMonthListValues) {

        List<CombinedOutOfStockPercentage> combinedOutOfStockPercentages = new ArrayList<CombinedOutOfStockPercentage>();

        for(Integer month : sixMonthListValues) {

            String monthLabel = translateMonth(month);

            Map<Integer, Integer> stockMap = monthlyMaps.get(month);
            List<Integer> productIds = monthlyProductIds.get(month);

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

            BigDecimal combinedPercent = calculateCombinedPercentage();
            combinedOutOfStockPercentages.add(new CombinedOutOfStockPercentage(monthLabel, combinedPercent));
        }

        return combinedOutOfStockPercentages;
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

    private String translateMonth(Integer month) {
        switch(month) {
                case 1:
                    return "Jan";
                case 2:
                    return "Feb";
                case 3:
                    return "Mar";
                case 4:
                    return "Apr";
                case 5:
                    return "May";
                case 6:
                    return "Jun";
                case 7:
                    return "Jul";
                case 8:
                    return "Aug";                 
                case 9:
                    return "Sep";
                case 10:
                    return "Oct";
                case 11:
                    return "Nov";
                case 12:
                    return "Dec";                   
            }

        return "Undefined";
    }
    
}