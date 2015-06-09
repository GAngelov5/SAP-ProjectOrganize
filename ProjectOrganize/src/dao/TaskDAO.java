package dao;

import java.util.Collection;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import models.Project;
import models.Task;

@Singleton
public class TaskDAO {

	@PersistenceContext
	private EntityManager em;

	public TaskDAO() {

	}

	public TaskDAO(EntityManager em) {
		this.em = em;
	}

	public void addTask(Task task) {
		em.persist(task);
	}

	public Task findById(Long id) {
		return em.find(Task.class, id);
	}

	public Collection<Task> getAllTasks() {
		String textQuery = "SELECT t FROM Task t";
		TypedQuery<Task> query = em.createNamedQuery(textQuery, Task.class);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Collection<Task> getAllTasks(Project project) {
		long projectId = project.getId();
		String textQuery = "SELECT t FROM Task t WHERE t.ProjectId =:projectId";
		TypedQuery<Task> query = em.createNamedQuery(textQuery, Task.class)
				.setParameter("projectId", projectId);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	// //////////////
	// public Collection<Comment> findTaskComments(Task task) {
	// long taskId = task.getId();
	// String textQuery = "select c from Comment c where c.task.id =:taskId";
	// TypedQuery<Comment> query = em.createQuery(textQuery,
	// Comment.class).setParameter("TaskId", task.getId());
	// return query.getResultList();
	// }
	// /////////////////////
}
