package forum.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import forum.entity.Comment;
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
	public Topic getTopic(long ident) {
		try {
			Topic t = (Topic) entityManager.createQuery("SELECT t FROM Topic t WHERE t.ident = :ident")
					.setParameter("ident", ident).getSingleResult();
			return t;

		} catch (NoResultException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Topic> getTopics() {
		return entityManager.createQuery("SELECT t from Topic t ").getResultList();
	}

	@Override
	public void deleteTopic(Topic topic) {
		entityManager.createQuery("DELETE t FROM Topics t WHERE t.title=:title").setParameter("title", topic)
				.getResultList();
		return;
	}

}
