package forum.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import forum.entity.Comment;
import forum.services.CommentService;

@Transactional
public class CommentServiceJPA implements CommentService{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addComment(Comment comment) {
		// TODO Auto-generated method stub		
	}

	@Override
	public List<Comment> getComments(String topic) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteComment(Comment comment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateComment(Comment comment) {
		// TODO Auto-generated method stub
		
	}

}
