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
import dao.UserDAO;

@Stateless
@Path("user")
public class UserManager {

	@EJB
	private UserDAO userDAO;

	@Inject
	private UserContext userContext;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void registerUser(User newUser) {
		userDAO.addUser(newUser);
		userContext.setCurrentUser(newUser);
	}
	
	@Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(User user) {
        boolean isUserValid = userDAO.validateUserCredentials(user.getUsername(), user.getPassword());
        if (!isUserValid) {
            return Response.status(HttpURLConnection.HTTP_UNAUTHORIZED).build();
        }
        userContext.setCurrentUser(user);
        return Response.ok().build();
    }
	
	@Path("allusers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		users.addAll(userDAO.getAllUsers());
		return users;
	}
}
