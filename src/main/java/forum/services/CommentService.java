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

// TODO: Auto-generated Javadoc
/**
 * The Interface CommentService.
 */
public interface CommentService {
	
	/**
	 * Adds the comment.
	 *
	 * @param comment the comment
	 */
	void addComment(Comment comment);
	
	/**
	 * Gets the comments.
	 *
	 * @param topic the topic
	 * @return the comments
	 */
	List<Comment> getComments(String topic);
	
	/**
	 * Delete comment.
	 *
	 * @param comment the comment
	 */
	@Transactional
	@Modifying
	void deleteComment(Comment comment);
	
	/**
	 * Update comment.
	 *
	 * @param ident the ident
	 * @param content the content
	 */
	void updateComment(Long ident, String content);

	/**
	 * Gets the comment.
	 *
	 * @param ident the ident
	 * @return the comment
	 */
	Comment getComment(Long ident);
	
	}
