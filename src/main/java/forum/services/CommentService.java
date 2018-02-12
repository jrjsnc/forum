/*
 *authors: Denisa Cekanova, Juraj Senic, Miro Sotak, Tomas Siman
 *project name: Movie forum
 *company: T-systems
 * (c)2018 
 */
package forum.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;

import forum.entity.Comment;


/**
 * The Interface CommentService.
 */
public interface CommentService {
	
	/**
	 * This method adds the comment.
	 *
	 * @param comment the comment
	 */
	void addComment(Comment comment);
	
	/**
	 * This method gets the comments.
	 *
	 * @param topic the topic
	 * @return the comments
	 */
	List<Comment> getComments(String topic);
	
	
	/**
	 * This method updates the comment.
	 *
	 * @param ident the ident
	 * @param content the content
	 */
	void updateComment(Long ident, String content);

	/**
	 * This method gets the comment.
	 *
	 * @param ident the ident
	 * @return the comment
	 */
	Comment getComment(Long ident);
	
	}
