package services;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import models.User;
import dao.ProjectDAO;
import dao.UserDAO;

@Stateless
@Path("/user")
public class UserManager {

	@EJB
	private UserDAO userDao;
	
	@EJB
	private ProjectDAO projectDao;

	@Inject
	private UserContext userContext;
	
	@Path("/register")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void registerUser(User newUser) {
		newUser.setRole("User");
		userDao.addUser(newUser);
		userContext.setCurrentUser(newUser);
	}
	
	@Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(User user) {
        boolean isUserValid = userDao.validateUserCredentials(user.getUsername(), user.getPassword());
        if (!isUserValid) {
            return Response.status(HttpURLConnection.HTTP_UNAUTHORIZED).build();
        }
        userContext.setCurrentUser(userDao.findUserByName(user.getUsername()));
        return Response.ok().build();
    }
	
	@Path("/allUsers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		users.addAll(userDao.getAllUsers());
		return users;
	}
	
	@Path("/currentUser")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getCurrentUser() {
		if (userContext.getCurrentUser() == null) {
			return null;
		}
		return userContext.getCurrentUser().getUsername();
	}
	
	//admin can do it
	@Path("/createUser")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createNewUser(User u) {
		userDao.addUser(u);
		return Response.ok().build();
	}
	
	//admin make regular user administrator
	@Path("/makeAdmin")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response makeUserAdmin(User u) {
		userDao.changeUserRole(u);
		return Response.ok().build();
	}
	
	
}
