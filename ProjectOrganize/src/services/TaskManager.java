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
	public Task getTaskById(@PathParam("id") int id) {
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
	@Consumes(MediaType.APPLICATION_JSON)
	public Response assignTask(User user, @PathParam("taskId") int taskId) {
		Task t = taskDao.findById(taskId);
		User u = userDao.findUserByName(user.getUsername());
		if (u != null) {
			taskDao.assignUserToTask(t, u);
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
	public Response editTask(Task task) { 
		taskDao.editTask(task);
		return Response.ok().build();
	}
	
	
	//Changing status of task INITIAL -> IN_PROGRESS
	@POST
	@Path("/changeStatus/{taskId}")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response changeStatus(String status, @PathParam("taskId") int taskId) {
		Task t = taskDao.findById(taskId);
		if (t != null && t.getStatus() != status) {
			if (taskDao.changeStatus(t, status) == 1);
				return Response.ok().build();
		}
		return Response.ok().build();
	}
	
	
	//Adding and showing comments
	
	@POST
	@Path("/comment/{taskId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response commentTask(Comment comment, @PathParam("taskId") int taskId) {
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
	@Path("/allComments/{taskId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Comment> getAllCommentsForTask(@PathParam("taskId") int id) {
		Task t = taskDao.findById(id);
		return commentDao.getAllComentsByTask(t);
	}
	
	
	//Mark important tasks
	@POST
	@Path("/admin/markTask/{taskId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response markImportantTask(@PathParam("taskId") int taskId) {
		Task t = taskDao.findById(taskId);
		t.setReporter(userContext.getCurrentUser());
		taskDao.editTask(t);
		return Response.ok().build();
	}
	
	@GET
	@Path("/admin/markedTask")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Task> getMarkedTasksForUser() {
		return taskDao.getMarkedTasks(userContext.getCurrentUser());
	}
	
	
}
