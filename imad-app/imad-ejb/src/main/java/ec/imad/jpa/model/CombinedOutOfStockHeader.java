package ec.imad.jpa.model;

import java.io.Serializable;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="IMAD_ACOMBINED_OUT_OF_STOCK_HEADER")
public class CombinedOutOfStockHeader implements Serializable {

    private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private BigDecimal number; // current value
    private String trend; // "Up", "Down", "None"
    private String state; // "Good", "Warning", "Error"


    public CombinedOutOfStockHeader() {
    }

    public CombinedOutOfStockHeader(BigDecimal number, String trend, String state) {
        this.number = number;
        this.trend = trend;
        this.state = state;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getNumber() {
        return this.number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public String getTrend() {
        return this.trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {

        return "{\"number\": \"" + getNumber()
            + "\",\"trend\": \"" + getTrend()
            + "\",\"state\": \"" + getState()
            + "\"}";
    }
}