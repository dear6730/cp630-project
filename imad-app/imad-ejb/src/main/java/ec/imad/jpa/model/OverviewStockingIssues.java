package ec.imad.jpa.model;

import java.math.BigDecimal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="IMAD_ASTOCK_ISSUES")
public class OverviewStockingIssues implements Serializable {

    private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="percentage_out_of_stock")
    private BigDecimal percentageOutOfStock;
    @Column(name="percentage_nearly_out_of_stock")
    private BigDecimal percentageNearlyOutOfStock;


    public OverviewStockingIssues() {
    }

    public OverviewStockingIssues(BigDecimal percentageOutOfStock, BigDecimal percentageNearlyOutOfStock) {
        this.percentageOutOfStock = percentageOutOfStock;
        this.percentageNearlyOutOfStock = percentageNearlyOutOfStock;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPercentageOutOfStock() {
        return percentageOutOfStock;
    }

    public void setPercentageOutOfStock(BigDecimal percentageOutOfStock) {
        this.percentageOutOfStock = percentageOutOfStock;
    }

    public BigDecimal getPercentageNearlyOutOfStock() {
        return percentageNearlyOutOfStock;
    }

    public void setPercentageNearlyOutOfStock(BigDecimal percentageNearlyOutOfStock) {
        this.percentageNearlyOutOfStock = percentageNearlyOutOfStock;
    }
    
    @Override
    public String toString() {
        return "{\"percentageOutOfStock\": \"" + getPercentageOutOfStock() 
                    + "\", \"percentageNearlyOutOfStock\": \"" + getPercentageNearlyOutOfStock()   
                    + "\"}";
    }
}