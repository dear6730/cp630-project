package ec.imad.jpa.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
Represents the stock in the system as at the last day of a Calendar month
in the past. Used for research and analysis purposes.
*/

@Entity
@Table(name="IMAD_THISTORICAL_STOCK")
public class HistoricalStock implements Serializable{
    
    private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer quantity;
    private Integer year;
    private Integer month;

    @ManyToOne
    @JoinColumn(name="PRODUCT_ID", nullable = false)
    private Product product;
    
    @ManyToOne
    @JoinColumn(name="LOCATION_ID", nullable = false)
    private Location location;

    public HistoricalStock() {
    }

    public HistoricalStock(Integer id, Integer quantity, Integer year, Integer month, Product product, Location location) {
        this.id = id;
        this.quantity = quantity;
        this.year = year;
        this.month = month;
        this.product = product;
        this.location = location;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return this.month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "HistoricalStock {" +
            " id='" + getId() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", month='" + getMonth() + "'" +
            ", year='" + getYear() + "'" +
            ", product='" + getProduct() + "'" +
            ", location='" + getLocation() + "'" +
            "}";
    }
}