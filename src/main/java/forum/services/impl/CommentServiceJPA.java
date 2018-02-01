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

@Transactional
public class CommentServiceJPA implements CommentService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addComment(Comment comment) {
		entityManager.persist(comment);
	}

	@Override
	public List<Comment> getComments(String topic) {
		return entityManager.createQuery("SELECT c FROM Comment c WHERE c.topic= :topic ORDER BY c.createdOn")
				.setParameter("topic", topic).getResultList();
	}
	
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

	@Override
	public void deleteComment(Comment comment) {
		entityManager.createQuery("DELETE FROM Comment c WHERE c.ident= :ident")
				.setParameter("ident", comment.getIdent()).executeUpdate();

	}	
	
	@Override
	public void updateComment(Long ident, String content) {
		Comment comment = entityManager.find(Comment.class, ident);
		comment.setContent(content);
	}

}
