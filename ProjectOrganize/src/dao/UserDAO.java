package dao;

import java.security.MessageDigest;
import java.util.Collection;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import models.User;

@Singleton
public class UserDAO {

	@PersistenceContext
	private EntityManager em;
	
	public UserDAO() {
		
	}
	 
	public UserDAO(EntityManager em) {
		this.em = em;
	}

	public void addUser(User user) {
		user.setPassword(getHashedPassword(user.getPassword()));
		em.persist(user);
	}

	public boolean validateUserCredentials(String userName, String password) {
		String txtQuery = "SELECT u FROM User u WHERE u.username=:userName AND u.password=:password";
		TypedQuery<User> query = em.createQuery(txtQuery, User.class);
		query.setParameter("userName", userName);
		query.setParameter("password", getHashedPassword(password));
		return queryUser(query) != null;
	}
	
	public User findUserById(int id) {
		return em.find(User.class, id);
	}

	public User findUserByName(String userName) {
		String txtQuery = "SELECT u FROM User u WHERE u.username = :userName";
		TypedQuery<User> query = em.createQuery(txtQuery, User.class);
		query.setParameter("userName", userName);
		return queryUser(query);
	}
	
	public void removeUserById(int id) {
		em.remove(this.findUserById(id));
	}

	private User queryUser(TypedQuery<User> query) {
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	private String getHashedPassword(String password) {
		try {
			MessageDigest mda = MessageDigest.getInstance("SHA-512");
			password = new String(mda.digest(password.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return password;
	}

	public Collection<User> getAllUsers() {
		String queryTxt = "SELECT u FROM User u";
		TypedQuery<User> query = em.createQuery(queryTxt, User.class);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	
}
