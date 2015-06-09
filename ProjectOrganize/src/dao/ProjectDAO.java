package dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import models.Project;


public class ProjectDAO {
	
	private EntityManager em;
	
	public ProjectDAO() {
		
	}
	
	public ProjectDAO(EntityManager em) {
		this.em = em;
	}
	
	public void addProject (Project project) {
		em.persist(project);
	}

//	public void createProject(Project project){
//		em.persist(project);
//		if(project.getMembers() != null){
//			List<User> members = new ArrayList<User>(project.getMembers());
//			//ConcurrentModification Exception
//			for (User member : members) {
//				this.addMemberInProject(member, project);
//			}
//		}
//	}
//	
//	public void addMemberInProject(User member, Project project) {
//		User userFromDB = userDao.findUserById(member.getId());
//		List<User> members = project.getMembers();
//		if(!members.contains(userFromDB)){
//			members.add(userFromDB);
//			em.merge(project); 
//			userFromDB.getProjects().add(project);
//			em.merge(userFromDB); 
//		}
//	}
//	
//	public void removeMemberFromProject(User member, Long projectId) {
//		Project project = this.findProjectById(projectId);
//		User userFromDB = userDao.findUserById(member.getId());
//		userFromDB.getProjects().remove(project);
//		em.merge(userFromDB);
//	}
	
	public Project updateProject(Project project) {
		return em.merge(project);
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
	
}
