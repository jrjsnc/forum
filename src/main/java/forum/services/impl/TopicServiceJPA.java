package forum.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import forum.entity.Topic;
import forum.services.TopicService;

@Transactional
public class TopicServiceJPA implements TopicService {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Topic> getTopics(String name) {

		
		return entityManager.createQuery("SELECT * FROM Topic ").getResultList();
	}

	@Override
	public void addTopic(Topic topic) {
		
		entityManager.persist(topic);
		
	}

	@Override
	public List<Topic> deleteTopics(Topic topic) {

		return entityManager.createQuery("DELETE t FROM Topic t WHERE t.name=:name").setParameter("name", topic ).getResultList();
	}
	
	
	

}
