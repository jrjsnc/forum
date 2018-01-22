package forum.services.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class UserServiceJPA {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void register(User user) {
		entityManager.per
		
		
	}
	
	
	
	
	

}
