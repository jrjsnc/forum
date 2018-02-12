/*
 *authors: Denisa Cekanova, Juraj Senic, Miro Sotak, Tomas Siman
 *project name: Movie forum
 *company: T-systems
 * (c)2018 
 */
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


// TODO: Auto-generated Javadoc
/**
 * The Class TagServiceJPA.
 */
@Transactional
public class TagServiceJPA implements TagService{
	
	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see forum.services.TagService#addTag(forum.entity.Tag)
	 */
	@Override
	public void addTag(Tag tag) throws DataIntegrityViolationException {
		entityManager.persist(tag);		
	}
	
	/* (non-Javadoc)
	 * @see forum.services.TagService#getAllTags()
	 */
	@Override
	public List<Tag> getAllTags() {
		return entityManager.createQuery("SELECT t from Tag t ").getResultList();
	}

	/* (non-Javadoc)
	 * @see forum.services.TagService#deleteTag(forum.entity.Tag)
	 */
	@Override
	public void deleteTag(Tag tag) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see forum.services.TagService#getTag(java.lang.Long)
	 */
	@Override
	public Tag getTag(Long ident) {
		return entityManager.find(Tag.class, ident);
	}
	
	@Override
	public Tag getTagByName(String name) {		
		try {
			return (Tag) entityManager
					.createQuery("SELECT t FROM Tag t WHERE name=:name")
					.setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}	
	}
	
	/* (non-Javadoc)
	 * @see forum.services.TagService#updateTag(java.lang.Long, java.lang.String)
	 */
	@Override
	public void updateTag(Long ident, String name) {
		Tag tag = entityManager.find(Tag.class, ident);
		tag.setName(name);		
	}

	
	
}
