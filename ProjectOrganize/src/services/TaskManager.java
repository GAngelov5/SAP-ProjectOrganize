package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import models.TaskHistory;
import models.User;
import dao.CommentDAO;
import dao.TaskDAO;
import dao.UserDAO;
import events.EmailSender;

@Stateless
@Path("/task")
public class TaskManager {

	@EJB
	private TaskDAO taskDao;

	@EJB
	private UserDAO userDao;

	@EJB
	private CommentDAO commentDao;

	@EJB
	private EmailSender emailSender;

	@Inject
	private UserContext userContext;

	@GET
	@Path("/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Task getTaskById(@PathParam("id") int id) {
		return taskDao.findById(id);
	}

	// admin
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

		Long initialTasks = taskDao.findUserTaskNumberByStatus(new Long(u.getId()),
				Status.INITIAL);

		Long inProgressTasks = taskDao.findUserTaskNumberByStatus(new Long(u.getId()),
				Status.IN_PROGRESS);

		if (initialTasks > 2 || inProgressTasks > 2) {
			taskDao.assignUserToTask(t, u);
			return Response
					.ok()
					.entity("User has already two task which are in initial status or in progress")
					.build();
		} else {
			taskDao.assignUserToTask(t, u);			
			return Response.ok().build();
		}
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

	// Changing status of task INITIAL -> IN_PROGRESS
	@POST
	@Path("/changeStatus/{taskId}")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response changeStatus(String status, @PathParam("taskId") int taskId) {
		Task t = taskDao.findById(taskId);
		if (t != null && t.getStatus() != status) {
			if (taskDao.changeStatus(t, status) == 1)
				return Response.ok().build();
		}
		return Response.ok().build();
	}

	// Adding and showing comments

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

	// Mark important tasks
	@POST
	@Path("/admin/markTask/{taskId}")
	@Consumes(MediaType.APPLICATION_JSON)
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

	@POST
	@Path("/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editTask(Task task) {
		if (taskDao.editTask(task)) {
			long currentTime = System.currentTimeMillis();
			Timer timer = emailSender.setTimer(currentTime);
			emailSender.sendAutomaticEmail(timer, task,
					userContext.getCurrentUser());
		}
		return Response.ok().build();
	}

	@GET
	@Path("/allUpdates/{taskId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TaskHistory> getAllUpdatesForTask(
			@PathParam("taskId") int taskId) {
		return taskDao.getAllUpdatesForTask(taskId);
	}

	@DELETE
	@Path("/cleanHistory/{taskId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cleanTaskHistory(@PathParam("taskId") int taskId) {
		taskDao.cleanHistory(taskId);
		return Response.ok().build();
	}
}
