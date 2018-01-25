package forum.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.validator.constraints.Email;

import forum.entity.ForumUser;
import forum.entity.Restriction;
import forum.entity.Topic;
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
			ForumUser fu = (ForumUser)entityManager.createQuery("SELECT u FROM ForumUser u WHERE u.login = :login").setParameter("login", login)
					.getSingleResult();
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
}
