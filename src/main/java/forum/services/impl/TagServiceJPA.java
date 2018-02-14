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

import forum.entity.Tag;
import forum.services.TagService;

/**
 * The Class TagServiceJPA. This class implements tag services.
 */
@Transactional
public class TagServiceJPA implements TagService {

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	/*
	 * This method adds the tag.
	 * 
	 * @see forum.services.TagService#addTag(forum.entity.Tag)
	 */
	@Override
	public void addTag(Tag tag) throws DataIntegrityViolationException {
		entityManager.persist(tag);
	}

	/*
	 * This method gets all tags.
	 * 
	 * @see forum.services.TagService#getAllTags()
	 */
	@Override
	public List<Tag> getAllTags() {
		return entityManager.createQuery("SELECT t from Tag t ").getResultList();
	}

	/*
	 * This method gets the tag.
	 * 
	 * @see forum.services.TagService#getTag(java.lang.Long)
	 */
	@Override
	public Tag getTag(Long ident) {
		return entityManager.find(Tag.class, ident);
	}

	/*
	 * This method returns tag by name.
	 * 
	 * @see forum.services.TagService#updateTag(java.lang.Long, java.lang.String)
	 */
	@Override
	public Tag getTagByName(String name) {
		try {
			return (Tag) entityManager.createQuery("SELECT t FROM Tag t WHERE name=:name").setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/*
	 * This method updates the tag.
	 * 
	 * @see forum.services.TagService#getTag(java.lang.Long)
	 */
	@Override
	public void updateTag(Long ident, String name) throws DataIntegrityViolationException {
		Tag tag = entityManager.find(Tag.class, ident);
		tag.setName(name);
	}

}
