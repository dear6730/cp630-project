package ec.imad.jpa.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="IMAD_ATOTAL_STOCK_CATEGORY")
public class TotalStockCategory implements Serializable {

    private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private BigDecimal value;


    public TotalStockCategory() {
    }

    public TotalStockCategory(String name, BigDecimal value) {
        this.name = name;
        this.value = value;
    }

    public TotalStockCategory(Integer id, String name, BigDecimal value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{ \"measureName\": \"" + getName() + "\",\"value\":" + getValue() + "}";
    }
}