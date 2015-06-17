package dao;

import java.util.Collection;
import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import models.Project;
import models.User;

@Singleton
public class ProjectDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	public ProjectDAO() {
		
	}
	
	public ProjectDAO(EntityManager em) {
		this.em = em;
	}
	
	public void addProject (Project project) {
		em.persist(project);
	}

	public Collection<Project> getAllProjects() {
		String textQuery = "SELECT p FROM Project p";
		TypedQuery<Project> query = em.createQuery(textQuery, Project.class);
		return query.getResultList();
	}
	
	public Project findProjectById(Long id) {
		return em.find(Project.class, id);
	}
	
	public long getProjectIdByName(String name) {
		String textQuery = "SELECT p FROM Project p WHERE p.name =:projectName";
		TypedQuery<Project> query = em.createQuery(textQuery, Project.class);
		query.setParameter("projectName", name);
		try {
			return query.getSingleResult().getId();
		} catch (NoResultException e) {
			return 0;
		}
	}
	
	public void removeProject(Project project) {
		long id = project.getId();
		em.remove(this.findProjectById(id));
	}
	
	public List<User> getUsersFromProject(Project project) {
		return project.getUsers();
	}
	
	
}
