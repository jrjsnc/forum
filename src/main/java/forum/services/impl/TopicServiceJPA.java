package forum.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import forum.entity.Topic;
import forum.services.TopicService;

@Transactional
public class TopicServiceJPA implements TopicService{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addTopic(Topic topic) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Topic> getTopics() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteTopic(Topic topic) {
		// TODO Auto-generated method stub
		
	}

}
