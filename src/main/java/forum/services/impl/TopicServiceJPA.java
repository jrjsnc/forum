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

import forum.entity.Comment;
import forum.entity.Topic;
import forum.services.TopicService;

// TODO: Auto-generated Javadoc
/**
 * The Class TopicServiceJPA.
 */
@Transactional
public class TopicServiceJPA implements TopicService {

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see forum.services.TopicService#addTopic(forum.entity.Topic)
	 */
	@Override
	public void addTopic(Topic topic) {
		entityManager.persist(topic);

	}

	/* (non-Javadoc)
	 * @see forum.services.TopicService#getTopics()
	 */
	@Override
	public List<Topic> getTopics() {
		return entityManager.createQuery("SELECT t from Topic t ").getResultList();
	}
	
	/* (non-Javadoc)
	 * @see forum.services.TopicService#getTopic(java.lang.Long)
	 */
	@Override
	public Topic getTopic(Long ident) {		
		return entityManager.find(Topic.class, ident);
	}

	/* (non-Javadoc)
	 * @see forum.services.TopicService#deleteTopic(forum.entity.Topic)
	 */
	@Override
	public void deleteTopic(Topic topic) {
		
		Topic t = (Topic)entityManager.createQuery("SELECT t FROM Topic t JOIN FETCH t.comments WHERE t.ident = :ident")
				.setParameter("ident", topic.getIdent()).getSingleResult();
		
		t.getComments().clear();		
		
		entityManager.createQuery("DELETE FROM Topic t WHERE t.title=:title").setParameter("title", topic.getTitle())
				.executeUpdate();		
	}
	
}
