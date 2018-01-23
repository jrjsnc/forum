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
	public void addTopic(Topic topic) {
		entityManager.persist(topic);

	}

	@Override
	public List<Topic> getTopics() {

		return entityManager.createQuery("SELECT * from Topics  DESC").getResultList();
	}

	@Override
	public void deleteTopic(Topic topic) {
		entityManager.createQuery("DELETE t FROM Topics t WHERE t.name=:name").setParameter("name", topic)
				.getResultList();
		return;
	}

}
