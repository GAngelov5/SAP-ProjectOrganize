package services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import models.Project;
import models.User;
import dao.ProjectDAO;
import dao.UserDAO;

//Admin can do this
@Stateless
@Path("/admin/projects")
public class ProjectManager {
	
	@EJB
	private ProjectDAO projectDao;
	
	@EJB
	private UserDAO userDao;
	
	@POST
	@Path("/createProject")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createProject(Project project) {
		projectDao.addProject(project);
		return Response.ok().build();
	}
	
	@POST
	@Path("/addMember")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addMemberToProject(User user) {
		userDao.addUser(user);
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/deleteMember")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteMemberFromProject(User user) {
		userDao.removeUserById(user.getId());
		return Response.ok().build();
	}
	
	@GET
	@Path("/allProjects")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAllProjects() {
		List<Project> allProjects = new ArrayList<Project>();
		allProjects.addAll(projectDao.getAllProjects());
		return allProjects.toString();
	}
}
