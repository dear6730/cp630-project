package ec.stats.jpa;

import java.util.List;

import javax.ejb.Remote;

import ec.stats.jpa.model.User;

@Remote
public interface UserDao {
	public void addUser(User user);
	public User getUser(String name, String password);
	public User getUser(String name);
	public List<User> getAllUser();
}