package ec.imad.jpa.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="IMAD_ATOP_5_PRODUCTS")
public class Top5Products implements Serializable {

    private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private BigDecimal value;


    public Top5Products() {
    }

    public Top5Products(String name, BigDecimal value) {
        this.name = name;
        this.value = value;
    }

    public Top5Products(Integer id, String name, BigDecimal value) {
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
        return "{\"Product\": \"" + getName() + "\",\"Stock Value\":" + getValue() + "}";
    }
}