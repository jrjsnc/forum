package forum.services.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import forum.entity.User;
import forum.services.UserService;

@Transactional
public class UserServiceJPA implements UserService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void register(User user) {
		entityManager.persist(user);

	}

	@Override
	public User login(String login, String password) {

		try {
			return (User) entityManager
					.createQuery("SELECT u FROM User u WHERE u.login = :login AND u.password =:password")
					.setParameter("login", login).setParameter("password", password).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
