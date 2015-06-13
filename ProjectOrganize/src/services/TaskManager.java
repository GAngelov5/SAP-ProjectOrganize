package services;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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

import models.Task;
import dao.TaskDAO;

@Stateless
@Path("task")
public class TaskManager {
	
	@EJB
	private TaskDAO taskDao;
	
	@Inject
	private UserContext userContext;
	
	
	@GET
	@Path("id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Task getTaskById(@PathParam("id") Long id) {
		return taskDao.findById(id);
	}
	
	@POST
	@Path("newTask")
	@Consumes(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response createNewTask(Task newTask) {
		
		
		return Response.ok().build();
	}

	
	@POST
	@Path("edit")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "Administrator" })
	public Response editIssue(Task task) { 
	//	taskDao.changeStatus(task);
		
		return Response.ok().build();
	}
}
