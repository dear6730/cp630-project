package ec.imad.jpa.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="IMAD_TPRODUCT")
public class Product implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private Category category;

    @Column(name="global_reorder_point")
    private Integer globalReorderPoint;


    public Product() {
    }

    public Product(String name, BigDecimal price, Category category, Integer globalReorderPoint) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.globalReorderPoint = globalReorderPoint;
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

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getGlobalReorderPoint() {
        return this.globalReorderPoint;
    }

    public void setGlobalReorderPoint(Integer globalReorderPoint) {
        this.globalReorderPoint = globalReorderPoint;
    }

    @Override
    public String toString() {
        return "Product {" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", price='" + getPrice() + "'" +
            ", globalReorderPoint='" + getGlobalReorderPoint() + "'" +
            ", category='" + getCategory() + "'" +
            "}";
    }
}