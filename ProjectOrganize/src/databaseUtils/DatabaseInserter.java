package databaseUtils;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import models.Role;
//import models.Role;
import models.User;
import dao.UserDAO;

@Singleton
@Startup
public class DatabaseInserter {
	
	@EJB
	private UserDAO userDao;
	
	@PersistenceContext
	private EntityManager em;

	@PostConstruct
	public void insertAdmin() {
		if (userDao.findUserByName("admin") == null) {
			User user = new User();
			user.setUsername("admin");
			user.setPassword("admin");
			user.setFullName("Admin Admin");
			user.setEmail("admin@admin.com");
			user.setRole(Role.ADMINISTRATOR);
			userDao.addUser(user);
		}
	
	}	
	
}
