package dao;

import java.util.Collection;
import java.util.Date;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import models.Comment;
import models.Task;
import models.User;

@Singleton
public class CommentDAO {
	
	@PersistenceContext
	private EntityManager em;

	public CommentDAO() {
	}
	
	public CommentDAO(EntityManager em) {
		this.em = em;
	}

	public void addComment(Comment comment) {
		em.persist(comment);
	}

	public Collection<Comment> getAllComentsByTask(Task task) {
		String textQuery = "SELECT c FROM Comment c WHERE c.task =:task";
		TypedQuery<Comment> query = em.createQuery(textQuery, Comment.class)
				.setParameter("task", task);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Collection<Comment> getAllCommentsByUser(User user) {
		long userId = user.getId();
		String textQuery = "SELECT c FROM Comment c WHERE c.userId =:userId";
		TypedQuery<Comment> query = em.createQuery(textQuery, Comment.class)
				.setParameter("userId", userId);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Collection<Comment> getAllCommentsByDate(Date date) {
		String textQuery = "SELECT c FROM Comment c WHERE c.dateAndHour =:date";
		TypedQuery<Comment> query = em.createNamedQuery(textQuery,
				Comment.class).setParameter("date", date);

		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	

}
