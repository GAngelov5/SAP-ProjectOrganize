package dao;

import java.util.Collection;
import java.util.Date;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import models.Project;
import models.Task;
import models.TaskHistory;
import models.User;
import services.UserContext;

@Singleton
public class TaskDAO {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserContext userContext;
	
	public TaskDAO() {
	}

	public TaskDAO(EntityManager em) {
		this.em = em;
	}

	public void addTask(Task task) {
		em.persist(task);
	}

	public Task findById(int id) {
		return em.find(Task.class, id);
	}

	public Collection<Task> getAllTasks() {
		String textQuery = "SELECT t FROM Task t";
		TypedQuery<Task> query = em.createQuery(textQuery, Task.class);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Collection<Task> getAllTasks(Project project) {
		long projectId = project.getId();
		String textQuery = "SELECT t FROM Task t WHERE t.ProjectId =:projectId";
		TypedQuery<Task> query = em.createQuery(textQuery, Task.class)
				.setParameter("projectId", projectId);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Task findTaskByName(String taskName) {
		Task t = new Task();
		String textQuery = "SELECT t FROM Task t WHERE t.name = :taskName";
		TypedQuery<Task> query = em.createQuery(textQuery, Task.class)
				.setParameter("taskName", taskName);
		if (query.getSingleResult() != null) {
			t = query.getSingleResult();
		}
		return t;
	}

	//User
	// ne raboti
	public void assignUserToTask(Task task, User user) {
		System.out.println("vleznah");
		user.getTasks().add(task);
		task.getUsers().add(user);
		em.persist(task);
		
		System.out.println("all users for current task: "+task.getUsers());
		System.out.println("all tasks for current user: "+user.getTasks());

//		Collection<Task> tasksForUser = getAllTaskForUser(user);
//		long taskId = task.getId();
//		String textQuery = "UPDATE Task t SET t.users = :moreUsers WHERE t.id = :taskId";
//		TypedQuery<Task> query = em.createQuery(textQuery, Task.class)
//				.setParameter("moreUsers", tasksForUser)
//				.setParameter("taskId", taskId);
//
//		return query.executeUpdate();

	}

	public Collection<Task> getAllTaskForUser(User u) {
		int userId = u.getId();
		String textQuery = "SELECT t FROM Task t WHERE t.id = :userId";
		TypedQuery<Task> query = em.createQuery(textQuery, Task.class)
				.setParameter("userId", userId);

		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public int changeStatus(Task task, String newStatus) {
		long taskId = task.getId();
		String textQuery = "UPDATE Task t SET t.status = :newStatus WHERE t.id = :taskId";
		TypedQuery<Task> query = em.createQuery(textQuery, Task.class)
				.setParameter("newStatus", newStatus)
				.setParameter("taskId", taskId);

		return query.executeUpdate();
	}

	public Long findUserTaskNumberByStatus(Long userId, String status) {
		String textQuery = "SELECT count(task.id) FROM Task task "
				+ "where task.assignee.id =:userId and "
				+ "issue.status.name=:status";
		TypedQuery<Long> query = em.createQuery(textQuery, Long.class);
		query.setParameter("userId", userId);
		query.setParameter("status", status);

		return query.getSingleResult();
	}

	// Update information to database
	public void editTask(Task task) {
		Task t = this.findById(task.getId());
		boolean saveHistory = !t.equals(task);
		em.merge(task);
		if (saveHistory) {
			this.saveHistory(task);
		}
	}

	public Collection<Task> getMarkedTasks(User user) {
		String txtQuery = "SELECT t From Task t WHERE t.reporter.id = :userId";
		TypedQuery<Task> query = em.createQuery(txtQuery, Task.class)
				.setParameter("userId", user.getId());
		
		try {
			return query.getResultList();
		} catch (NoResultException e){
			return null;
		}
	}
	
	private void saveHistory(Task task){ 
		TaskHistory history = new TaskHistory(task);
		history.setEditor(userContext.getCurrentUser());
		history.setUpdateDate(new Date());
		em.persist(history); 
	}

}
