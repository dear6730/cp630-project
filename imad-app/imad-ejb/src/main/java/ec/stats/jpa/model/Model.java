package ec.stats.jpa.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ecmodel")
public class Model implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue
    private Integer id;
    private String name;
	private String classname;
	private byte[] object;
    private Timestamp date;


    public Model() {
    }

    public Model(   String name, 
                    String classname, 
                    byte[] object, 
                    Timestamp date) {
        this.name = name;
        this.classname = classname;
        this.object = object;
        this.date = date;
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

    public String getClassname() {
        return this.classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public byte[] getObject() {
        return this.object;
    }

    public void setObject(byte[] object) {
        this.object = object;
    }

    public Timestamp getDate() {
        return this.date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }    

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", classname='" + getClassname() + "'" +
            ", object='" + getObject() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
