package ec.stats.jpa.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ecuser")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String ADMINISTRATOR = "Administrator";
	private static final String DEVELOPER = "Developer";
	private static final String GUEST = "Guest";

	@Id
    @GeneratedValue
    private Integer id;
    private String name;
	private String password;
	private Integer role;
	
    public User() { }
	
    public User(String name) {
       this.name = name;
    }
	
    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
	
	public User(Integer id, String name, String password, Integer role) {
        this.id = id;
        this.name = name;
		this.password = password;
		this.role = role;
    }

	public User(String name, String password, Integer role) {
        this.name = name;
		this.password = password;
		this.role = role;
    }
	
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) {  this.name = name; }
	public String getPassword() { return password; }
    public void setPassword(String password) {  this.password = password; }
    public Integer getRole() { return role; }
    public void setRole(Integer role) { this.role = role; }
    public String getRoleFormatted() { 
        String roleFormatted = GUEST;
        if(this.getRole() == 1) roleFormatted = ADMINISTRATOR;
        if(this.getRole() == 2) roleFormatted = DEVELOPER;
        return roleFormatted;
    }
    @Override
    public String toString() {
        return "User {" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}