package ec.imad.jpa.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="IMAD_TSTOCK")
public class Stock implements Serializable{
    
    private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name="PRODUCT_ID", nullable = false)
    private Product product;
    
    @ManyToOne
    @JoinColumn(name="LOCATION_ID", nullable = false)
    private Location location;

    public Stock() {
    }

    public Stock(Integer id, Integer quantity, Product product, Location location) {
        this.id = id;
        this.quantity = quantity;
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
        return "Stock {" +
            " id='" + getId() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", product='" + getProduct() + "'" +
            ", location='" + getLocation() + "'" +
            "}";
    }
}