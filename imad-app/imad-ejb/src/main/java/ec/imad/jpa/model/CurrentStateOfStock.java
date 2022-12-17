package ec.imad.jpa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="IMAD_ACURRENT_STATE_OF_STOCK")
public class CurrentStateOfStock implements Serializable {

    private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String sku;
    private String name;
    private Integer quantity;
    private String status;
    @Column(name="status_state")
    private String statusState;
    

    public CurrentStateOfStock() {
    }

    public CurrentStateOfStock(String sku, String name, Integer quantity, String status, String statusState) {
        this.sku = sku;
        this.name = name;
        this.quantity = quantity;
        this.status = status;
        this.statusState = statusState;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusState() {
        return statusState;
    }

    public void setStatusState(String statusState) {
        this.statusState = statusState;
    }    
    
    @Override
    public String toString() {
        return "{\"productSKU\": \"" + getSku() 
                    + "\", \"productName\": \"" + getName() 
                    + "\", \"quantity\": \""    + getQuantity()
                    + "\", \"status\": \""      + getStatus()
                    + "\", \"statusState\": \"" + getStatusState()   
                    + "\"}";
    }
}