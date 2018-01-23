package forum.services;

import java.util.List;

import forum.entity.Comment;

public interface CommentService {
	
	void addComment(Comment comment);
	
	List<Comment> getComments(String topic);
	
	void deleteComment(Comment comment);
	
	void updateComment(Comment comment);
	
	}
