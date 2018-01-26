package forum.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
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
	public List<Topic> getTopics() {
		return entityManager.createQuery("SELECT t from Topic t ").getResultList();
	}
	
	@Override
	public Topic getTopic(Long ident) {		
		return entityManager.find(Topic.class, ident);
	}

	@Override
	public void deleteTopic(Topic topic) {
		
		Topic t = (Topic)entityManager.createQuery("SELECT t FROM Topic t JOIN FETCH t.comments WHERE t.ident = :ident")
				.setParameter("ident", topic.getIdent()).getSingleResult();
		
		
		
		//int commentCount = t.getComments().size();
		
		t.getComments().clear();
		
//		for (int i = 0; i<commentCount; i++) {
//			t.removeComment(t.getComments().get(i));
//		}
		
		//t.removeComment(t.getComments().get(0));
		
		entityManager.createQuery("DELETE FROM Topic t WHERE t.title=:title").setParameter("title", topic.getTitle())
				.executeUpdate();		
	}
	
}
