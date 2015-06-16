package services;

import java.util.Collection;
import java.util.Date;

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

import models.Comment;
import models.Status;
import models.Task;
import models.User;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import dao.CommentDAO;
import dao.TaskDAO;
import dao.UserDAO;

@Stateless
@Path("/task")
public class TaskManager {
	
	@EJB
	private TaskDAO taskDao;
	
	@EJB
	private UserDAO userDao;
	
	@EJB
	private CommentDAO commentDao;
	
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
		newTask.setStatus(Status.INITIAL);
		taskDao.addTask(newTask);
		return Response.ok().build();
	}
	
	@POST
	@Path("/assignTask/{taskId}")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response assignTask(String username, @PathParam("taskId") Long taskId) {
		Task t = taskDao.findById(taskId);
		System.out.println(t.getName());
		User u = userDao.findUserByName(username);
		if (u != null) {
			if (taskDao.assignUserToTask(t, u) == 1);
				return Response.ok().build();
		}
		return Response.ok().build();
	}
	
	@GET
	@Path("/allTaskForUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Task> getAllTasksForUser() {
		return taskDao.getAllTaskForUser(userContext.getCurrentUser());
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
	
	
	//Need to take only 1 parameter !! 
	@POST
	@Path("/changeStatus/{taskId}")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response changeStatus(String status, @PathParam("taskId") Long taskId) {
		Task t = taskDao.findById(taskId);
		if (t != null && t.getStatus() != status) {
			if (taskDao.changeStatus(t, status) == 1);
				return Response.ok().build();
		}
		return Response.ok().build();
	}
	
	@POST
	@Path("/comment/{taskId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response commentTask(Comment comment, @PathParam("taskId") Long taskId) {
		Task t = taskDao.findById(taskId);
		if (t != null) {
			comment.setTask(t);
			comment.setDateAndHour(new Date());
			comment.setAuthor(userContext.getCurrentUser());
			commentDao.addComment(comment);
			return Response.ok().build();
		}
		return Response.ok().build();
	}
	
	@GET
	@Path("/comments/{taskId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Comment> getAllCommentsForTask(@PathParam("taskId") Long id) {
		Task t = taskDao.findById(id);
		return commentDao.getAllComentsByTask(t);
	}
}
