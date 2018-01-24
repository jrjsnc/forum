package forum.services.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import forum.entity.ForumUser;
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
			ForumUser fu = (ForumUser)entityManager
					.createQuery("SELECT u FROM ForumUser u WHERE u.login = :login").setParameter("login", login)
					.getSingleResult();
		} catch (NoResultException e) {
			return false;
		}
		return true;

	}
}
