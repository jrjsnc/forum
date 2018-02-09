/*
 *authors: Denisa Cekanova, Juraj Senic, Miro Sotak, Tomas Siman
 *project name: Movie forum
 *company: T-systems
 * (c)2018 
 */
package forum.services;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.multipart.MultipartFile;

import forum.entity.Comment;
import forum.entity.ForumUser;
import forum.entity.Restriction;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserService.
 */
public interface UserService {

	/**
	 * Register.
	 *
	 * @param user
	 *            the user
	 */
	void register(ForumUser user);

	/**
	 * Login.
	 *
	 * @param login
	 *            the login
	 * @param password
	 *            the password
	 * @return the forum user
	 */
	ForumUser login(String login, String password);

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	List<ForumUser> getUsers();

	/**
	 * Name taken.
	 *
	 * @param login
	 *            the login
	 * @return true, if successful
	 */
	boolean nameTaken(String login);

	/**
	 * Gets the user.
	 *
	 * @param ident
	 *            the ident
	 * @return the user
	 */
	ForumUser getUser(Long ident);

	/**
	 * Gets the user by email.
	 *
	 * @param email
	 *            the email
	 * @return the user by email
	 */
	ForumUser getUserByEmail(String email);

	/**
	 * Sets the restriction.
	 *
	 * @param ident
	 *            the ident
	 * @param restriction
	 *            the restriction
	 */
	void setRestriction(Long ident, Restriction restriction);

	/**
	 * Gets the image.
	 *
	 * @param login
	 *            the login
	 * @return the image
	 */
	byte[] getImage(String login);

	/**
	 * Delete user.
	 *
	 * @param user
	 *            the user
	 */
	@Transactional
	@Modifying
	void deleteUser(ForumUser user);

	/**
	 * Update user.
	 *
	 * @param ident
	 *            the ident
	 * @param login
	 *            the login
	 * @param email
	 *            the email
	 * @param password
	 *            the password
	 * @param userImage
	 *            the user image
	 */
	void updateUser(Long ident, String login, String email, String password, MultipartFile userImage);

	/**
	 * Toggle like.
	 *
	 * @param ident
	 *            the ident
	 * @param comment
	 *            the comment
	 */
	void toggleLike(Long ident, Comment comment);

	/**
	 * Gets the liked comments.
	 *
	 * @param ident
	 *            the ident
	 * @return the liked comments
	 */
	Set<Comment> getLikedComments(Long ident);
}
