package forum.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;

import forum.entity.Comment;

public interface CommentService {
	
	void addComment(Comment comment);
	
	List<Comment> getComments(String topic);
	
	@Transactional
	@Modifying
	void deleteComment(Comment comment);
	
	void updateComment(Comment comment);

	Comment getComment(Long ident);
	
	}
