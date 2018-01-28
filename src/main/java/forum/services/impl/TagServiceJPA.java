package forum.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import forum.entity.Tag;
import forum.services.TagService;


@Transactional
public class TagServiceJPA implements TagService{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addTag(Tag tag) {
		entityManager.persist(tag);		
	}
	
	@Override
	public List<Tag> getAllTags() {
		return entityManager.createQuery("SELECT t from Tag t ").getResultList();
	}

	@Override
	public void deleteTag(Tag tag) {
		// TODO Auto-generated method stub
		
	}

	
	
}
