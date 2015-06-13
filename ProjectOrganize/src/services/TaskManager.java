package services;

import java.util.Collection;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import models.Status;
import models.Task;
import models.User;
import dao.TaskDAO;
import dao.UserDAO;

@Stateless
@Path("/task")
public class TaskManager {
	
	@EJB
	private TaskDAO taskDao;
	
	@EJB
	private UserDAO userDao;
	
	@Inject
	private UserContext userContext;
	
	
	@GET
	@Path("/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Task getTaskById(@PathParam("id") Long id) {
		return taskDao.findById(id);
	}
	
	@POST
	@Path("/newTask")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createNewTask(Task newTask) {
		newTask.setStatus(Status.START);
		taskDao.addTask(newTask);
		return Response.ok().build();
	}
	
	@POST
	@Path("/assignTask")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response assignTask(String userName, String taskName) {
		User u = userDao.findUserByName(userName);
		Task t = taskDao.findTaskByName(taskName);
		if (t != null && u != null) {
			taskDao.assignUserToTask(t, u);
		}
		return Response.ok().build();
	}
	
	@GET
	@Path("/allTaskForUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Task> getAllTasksForUser() {
		return userContext.getCurrentUser().getTasks();
	}
	
	@GET
	@Path("/allTasks")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Task> getAllTasks() {
		return taskDao.getAllTasks();
	}
	
	@POST
	@Path("/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editIssue(Task task) { 
		return Response.ok().build();
	}
}
