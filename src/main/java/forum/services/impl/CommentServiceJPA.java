/*
 *authors: Denisa Cekanova, Juraj Senic, Miro Sotak, Tomas Siman
 *project name: Movie forum
 *company: T-systems
 * (c)2018 
 */
package forum.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import forum.entity.Comment;
import forum.entity.ForumUser;
import forum.entity.Restriction;
import forum.services.CommentService;

// TODO: Auto-generated Javadoc
/**
 * The Class CommentServiceJPA.
 */
@Transactional
public class CommentServiceJPA implements CommentService {

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see forum.services.CommentService#addComment(forum.entity.Comment)
	 */
	@Override
	public void addComment(Comment comment) {
		entityManager.persist(comment);
	}

	/* (non-Javadoc)
	 * @see forum.services.CommentService#getComments(java.lang.String)
	 */
	@Override
	public List<Comment> getComments(String topic) {
		return entityManager.createQuery("SELECT c FROM Comment c WHERE c.topic= :topic ORDER BY c.createdOn")
				.setParameter("topic", topic).getResultList();
	}
	
	/* (non-Javadoc)
	 * @see forum.services.CommentService#getComment(java.lang.Long)
	 */
	@Override
	public Comment getComment(Long ident) {
		try {
			Comment c = (Comment) entityManager
					.createQuery("SELECT c FROM Comment c WHERE c.ident = :ident")
					.setParameter("ident", ident).getSingleResult();			
			return c;
			
		} catch (NoResultException e) {
				e.printStackTrace();
			}

		return null;
	}

	/* (non-Javadoc)
	 * @see forum.services.CommentService#deleteComment(forum.entity.Comment)
	 */
	@Override
	public void deleteComment(Comment comment) {
		entityManager.createQuery("DELETE FROM Comment c WHERE c.ident= :ident")
				.setParameter("ident", comment.getIdent()).executeUpdate();

	}	
	
	/* (non-Javadoc)
	 * @see forum.services.CommentService#updateComment(java.lang.Long, java.lang.String)
	 */
	@Override
	public void updateComment(Long ident, String content) {
		Comment comment = entityManager.find(Comment.class, ident);
		comment.setContent(content);		
	}

}
