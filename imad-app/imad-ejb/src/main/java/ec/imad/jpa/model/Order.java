package ec.imad.jpa.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="IMAD_TORDER")
public class Order implements Serializable{
    
    private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date date;
    private Date required_date;
    private Date shipped_date;
    private String status;

    @ManyToOne
    @JoinColumn(name="LOCATION_ID", nullable = false)
    private Location location;


    public Order() {
    }

    public Order(Integer id, Date date, Date required_date, Date shipped_date, String status, Location location) {
        this.id = id;
        this.date = date;
        this.required_date = required_date;
        this.shipped_date = shipped_date;
        this.status = status;
        this.location = location;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getRequired_date() {
        return this.required_date;
    }

    public void setRequired_date(Date required_date) {
        this.required_date = required_date;
    }

    public Date getShipped_date() {
        return this.shipped_date;
    }

    public void setShipped_date(Date shipped_date) {
        this.shipped_date = shipped_date;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Order {" +
            " id='" + getId() + "'" +
            ", date='" + getDate() + "'" +
            ", required_date='" + getRequired_date() + "'" +
            ", shipped_date='" + getShipped_date() + "'" +
            ", status='" + getStatus() + "'" +
            ", location='" + getLocation() + "'" +
            "}";
    }
}