package ec.imad.jpa.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="IMAD_ATOTAL_STOCK_VALUE")
public class TotalStockValue implements Serializable {

    private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String city;
    @Column(name="category_name")
    private String categoryName;
    @Column(name="total_category")
    private BigDecimal totalCategory;

    public TotalStockValue() {
    }

    public TotalStockValue(String city, String categoryName, BigDecimal totalCategory) {
        this.city = city;
        this.categoryName = categoryName;
        this.totalCategory = totalCategory;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public BigDecimal getTotalCategory() {
        return this.totalCategory;
    }

    public void setTotalCategory(BigDecimal totalCategory) {
        this.totalCategory = totalCategory;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", city='" + getCity() + "'" +
            ", categoryName='" + getCategoryName() + "'" +
            ", totalCategory='" + getTotalCategory() + "'" +
            "}";
    }
}
