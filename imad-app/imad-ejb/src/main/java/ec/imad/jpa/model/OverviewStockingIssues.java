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
    @Column(name="out_of_stock_percentage")
    private BigDecimal outOfStockPercentage;
    @Column(name="nearly_out_of_stock_percentage")
    private BigDecimal nearlyOutOfStockPercentage;
    @Column(name="combined_percentage")
    private BigDecimal combinedPercentage;

    public OverviewStockingIssues() {
    }

    public OverviewStockingIssues(BigDecimal outOfStockPercentage, BigDecimal nearlyOutOfStockPercentage, BigDecimal combinedPercentage) {
        this.outOfStockPercentage = outOfStockPercentage;
        this.nearlyOutOfStockPercentage = nearlyOutOfStockPercentage;
        this.combinedPercentage = combinedPercentage;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getOutOfStockPercentage() {
        return outOfStockPercentage;
    }

    public void setOutOfStockPercentage(BigDecimal outOfStockPercentage) {
        this.outOfStockPercentage = outOfStockPercentage;
    }

    public BigDecimal getNearlyOutOfStockPercentage() {
        return nearlyOutOfStockPercentage;
    }

    public void setNearlyOutOfStockPercentage(BigDecimal nearlyOutOfStockPercentage) {
        this.nearlyOutOfStockPercentage = nearlyOutOfStockPercentage;
    }

    public BigDecimal getCombinedPercentage() {
        return combinedPercentage;
    }

    public void setCombinedPercentage(BigDecimal combinedPercentage) {
        this.combinedPercentage = combinedPercentage;
    }
    
    @Override
    public String toString() {
        return "{\"outOfStockPercentage\": \"" + getOutOfStockPercentage() 
                    + "\", \"nearlyOutOfStockPercentage\": \"" + getNearlyOutOfStockPercentage()   
                    + "\", \"combinedPercentage\": \"" + getCombinedPercentage()   
                    + "\"}";
    }
}