package forum.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;

import forum.entity.Comment;
import forum.entity.ForumUser;
import forum.entity.Tag;
import forum.services.TagService;


@Transactional
public class TagServiceJPA implements TagService{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addTag(Tag tag) throws DataIntegrityViolationException {
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

	@Override
	public Tag getTag(Long ident) {
		return entityManager.find(Tag.class, ident);
	}
	
	@Override
	public void updateTag(Long ident, String name) {
		Tag tag = entityManager.find(Tag.class, ident);
		tag.setName(name);		
	}

	
	
}
