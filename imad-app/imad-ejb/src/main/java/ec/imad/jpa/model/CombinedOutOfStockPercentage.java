package ec.imad.jpa.model;

import java.io.Serializable;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="IMAD_ACOMBINED_OUT_OF_STOCK_PERCENTAGE")
public class CombinedOutOfStockPercentage implements Serializable {

    private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String month;
    private BigDecimal stock;


    public CombinedOutOfStockPercentage() {
    }

    public CombinedOutOfStockPercentage(String month, BigDecimal stock) {
        this.month = month;
        this.stock = stock;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getStock() {
        return this.stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "{\"Month\": \"" + getMonth() + "\",\"Stock\":" + getStock() + "}";
    }
}