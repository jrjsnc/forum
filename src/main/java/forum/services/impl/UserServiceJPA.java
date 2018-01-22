package forum.services.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import forum.entity.User;

@Transactional
public class UserServiceJPA {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	
	
	
	
	

}
