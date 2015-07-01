package services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
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
	
	@GET
	@Path("/logout")
	public void logoutUser(@Context HttpServletRequest request, @Context HttpServletResponse response) {
		request.getSession().invalidate();
		try {
			response.sendRedirect(request.getContextPath() + "/index.html");
		} catch (IOException e) {
			e.printStackTrace();
		}	
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
	
	//admin make regular user administrator
	@Path("/admin/makeAdmin/{userId}")
	@GET
	public Response makeUserAdmin(@PathParam("userId") int userId) {
		User u = userDao.findUserById(userId);
		userDao.changeUserRole(u);
		return Response.ok().build();
	}
	
	
	
}
