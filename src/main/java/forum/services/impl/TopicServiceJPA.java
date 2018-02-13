/*
 *authors: Denisa Cekanova, Juraj Senic, Miro Sotak, Tomas Siman
 *project name: Movie forum
 *company: T-systems
 * (c)2018 
 */
package forum.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import forum.entity.Topic;
import forum.services.TopicService;

/**
 * The Class TopicServiceJPA. This class implements topic services.
 */
@Transactional
public class TopicServiceJPA implements TopicService {

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	/*
	 * This method adds the topic.
	 * 
	 * @see forum.services.TopicService#addTopic(forum.entity.Topic)
	 */
	@Override
	public void addTopic(Topic topic) {
		entityManager.persist(topic);

	}

	/*
	 * This method gets the topics.
	 * 
	 * @see forum.services.TopicService#getTopics()
	 */
	@Override
	public List<Topic> getTopics() {
		return entityManager.createQuery("SELECT t from Topic t ").getResultList();
	}

	/*
	 * This method gets the topic.
	 * 
	 * @see forum.services.TopicService#getTopic(java.lang.Long)
	 */
	@Override
	public Topic getTopic(Long ident) {
		return entityManager.find(Topic.class, ident);
	}

}
