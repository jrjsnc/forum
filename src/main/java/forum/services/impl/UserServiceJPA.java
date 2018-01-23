package forum.services.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import forum.entity.User;
import forum.services.UserService;

@Transactional
public class UserServiceJPA implements UserService{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void register(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User login(String login, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean nameTaken(String login) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
	
	
	

}
