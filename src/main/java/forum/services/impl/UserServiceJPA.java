package forum.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.web.multipart.MultipartFile;

import forum.entity.Comment;
import forum.entity.ForumUser;
import forum.entity.Restriction;
import forum.services.UserService;

@Transactional
public class UserServiceJPA implements UserService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void register(ForumUser user) {
		entityManager.persist(user);
	}

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

	@Override
	public List<ForumUser> getUsers() {
		return entityManager.createQuery("SELECT u FROM ForumUser u").getResultList();
	}

	@Override
	public ForumUser getUser(Long ident) {
		return entityManager.find(ForumUser.class, ident);
	}

	@Override
	public void setRestriction(Long ident, Restriction restriction) {
		ForumUser user = entityManager.find(ForumUser.class, ident);
		user.setRestriction(restriction);	
	}	
	
	@Override
	public void toggleLike(Long ident, Comment comment) {
		ForumUser user = entityManager.find(ForumUser.class, ident);
		if(!user.getLikedComments().contains(comment)) {
				user.getLikedComments().add(comment);
		} else {
			user.getLikedComments().remove(comment);
		}
	}
	
	@Override
	public Set<Comment> getLikedComments(Long ident) {
		ForumUser user = entityManager.find(ForumUser.class, ident);
		return user.getLikedComments();		
	}

	
	@Override
	public byte[] getImage(String login) {
		try {
			return (byte[]) entityManager.createQuery("SELECT u.userImage FROM ForumUser u WHERE u.login =:login")
					.setParameter("login", login).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void deleteUser(ForumUser user) {
		entityManager.createQuery("DELETE FROM Forum_user f WHERE f.ident= :ident")
		.setParameter("ident", user.getIdent()).executeUpdate();

		
	}

	@Override
	public void updateUser(Long ident, String login, String email, MultipartFile userImage) {
		System.err.println(ident);
		ForumUser user = entityManager.find(ForumUser.class, ident);
	
		user.setLogin(login);
		user.setEmail(email);
		try {
			user.setUserImage(userImage.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
