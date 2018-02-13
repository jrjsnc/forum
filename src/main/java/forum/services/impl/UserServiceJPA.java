/*
 *authors: Denisa Cekanova, Juraj Senic, Miro Sotak, Tomas Siman
 *project name: Movie forum
 *company: T-systems
 * (c)2018 
 */
package forum.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.dao.DataIntegrityViolationException;

import forum.entity.Comment;
import forum.entity.ForumUser;
import forum.entity.Restriction;
import forum.services.UserService;

/**
 * The Class UserServiceJPA This class implements user service methods
 */
@Transactional
public class UserServiceJPA implements UserService {

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	/*
	 * This method register the forum user.
	 * 
	 * @see forum.services.UserService#register(forum.entity.ForumUser)
	 */
	@Override
	public void register(ForumUser user) throws DataIntegrityViolationException {
		entityManager.persist(user);
	}

	/*
	 * This method login the forum user.
	 * 
	 * @see forum.services.UserService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public ForumUser login(String login, String password) {
		try {
			return (ForumUser) entityManager
					.createQuery("SELECT u FROM ForumUser u WHERE u.login = :login AND u.password =:password")
					.setParameter("login", login).setParameter("password", password).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/*
	 * This method reveal the name already taken in forum user list.
	 * 
	 * @see forum.services.UserService#nameTaken(java.lang.String)
	 */
	@Override
	public boolean nameTaken(String login) {
		try {
			ForumUser fu = (ForumUser) entityManager.createQuery("SELECT u FROM ForumUser u WHERE u.login = :login")
					.setParameter("login", login).getSingleResult();
		} catch (NoResultException e) {
			return false;
		}
		return true;
	}

	/*
	 * This method gets the list of all of the forum users.
	 * 
	 * @see forum.services.UserService#getUsers()
	 */
	@Override
	public List<ForumUser> getUsers() {
		return entityManager.createQuery("SELECT u FROM ForumUser u").getResultList();
	}

	/*
	 * This method gets the forum user.
	 * 
	 * @see forum.services.UserService#getUser(java.lang.Long)
	 */
	@Override
	public ForumUser getUser(Long ident) {
		return entityManager.find(ForumUser.class, ident);
	}

	/*
	 * This method gets the forum user by email.
	 * 
	 * @see forum.services.UserService#getUserByEmail(java.lang.String)
	 */
	@Override
	public ForumUser getUserByEmail(String email) {
		ForumUser fu = new ForumUser();
		try {
			fu = (ForumUser) entityManager.createQuery("SELECT u FROM ForumUser u WHERE u.email = :email")
					.setParameter("email", email).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return fu;
	}

	/*
	 * This method set the restrictions for the forum user.
	 * 
	 * @see forum.services.UserService#setRestriction(java.lang.Long,
	 * forum.entity.Restriction)
	 */
	@Override
	public void setRestriction(Long ident, Restriction restriction) {
		ForumUser user = entityManager.find(ForumUser.class, ident);
		user.setRestriction(restriction);
	}

	/*
	 * This method toggles like of the comment.
	 * 
	 * @see forum.services.UserService#toggleLike(java.lang.Long,
	 * forum.entity.Comment)
	 */
	@Override
	public void toggleLike(Long ident, Comment comment) {
		ForumUser user = entityManager.find(ForumUser.class, ident);
		if (!user.getLikedComments().contains(comment)) {
			user.getLikedComments().add(comment);
		} else {
			user.getLikedComments().remove(comment);
		}
	}

	/*
	 * This method gets the liked comments.
	 * 
	 * @see forum.services.UserService#getLikedComments(java.lang.Long)
	 */
	@Override
	public Set<Comment> getLikedComments(Long ident) {
		ForumUser user = entityManager.find(ForumUser.class, ident);
		return user.getLikedComments();
	}

	/*
	 * This method gets the forum user image.
	 * 
	 * @see forum.services.UserService#getImage(java.lang.String)
	 */
	@Override
	public byte[] getImage(String login) {
		try {
			return (byte[]) entityManager.createQuery("SELECT u.userImage FROM ForumUser u WHERE u.login =:login")
					.setParameter("login", login).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/*
	 * This method updates the forum user.
	 * 
	 * @see forum.services.UserService#updateUser(java.lang.Long, java.lang.String,
	 * java.lang.String, java.lang.String,
	 * org.springframework.web.multipart.MultipartFile)
	 */
	@Override
	public void updateUser(Long ident, String login, String email, String password, MultipartFile userImage) {
		System.err.println(ident);
		ForumUser user = entityManager.find(ForumUser.class, ident);

		user.setLogin(login);
		user.setEmail(email);
		user.setPassword(password);
		try {
			user.setUserImage(userImage.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
