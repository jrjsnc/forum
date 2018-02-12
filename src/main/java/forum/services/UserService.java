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


/**
 * The Interface UserService.
 */
public interface UserService {

	/**
	 * This method register the forum user.
	 *
	 * @param user
	 *            the user
	 */
	void register(ForumUser user);

	/**
	 * This method login the forum user.
	 *
	 * @param login
	 *            the login
	 * @param password
	 *            the password
	 * @return the forum user
	 */
	ForumUser login(String login, String password);

	/**
	 * This method gets the list of all of the forum users.
	 *
	 * @return the users
	 */
	List<ForumUser> getUsers();

	/**
	 * This method reveal the name already taken in forum user list.
	 *
	 * @param login
	 *            the login
	 * @return true, if successful
	 */
	boolean nameTaken(String login);

	/**
	 * This method gets the forum user.
	 *
	 * @param ident
	 *            the ident
	 * @return the user
	 */
	ForumUser getUser(Long ident);

	/**
	 * This method gets the forum user by email.
	 *
	 * @param email
	 *            the email
	 * @return the user by email
	 */
	ForumUser getUserByEmail(String email);

	/**
	 * This method set the restrictions for the forum user.
	 *
	 * @param ident
	 *            the ident
	 * @param restriction
	 *            the restriction
	 */
	void setRestriction(Long ident, Restriction restriction);

	/**
	 * This method gets the forum user image.
	 *
	 * @param login
	 *            the login
	 * @return the image
	 */
	byte[] getImage(String login);

	/**
	 * This method deletes the user.
	 *
	 * @param user
	 *            the user
	 */
	@Transactional
	@Modifying
	void deleteUser(ForumUser user);

	/**
	 * This method updates the forum user.
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
	 * This method toggles like of the comment.
	 *
	 * @param ident
	 *            the ident
	 * @param comment
	 *            the comment
	 */
	void toggleLike(Long ident, Comment comment);

	/**
	 * This method gets the liked comments.
	 *
	 * @param ident
	 *            the ident
	 * @return the liked comments
	 */
	Set<Comment> getLikedComments(Long ident);
}
