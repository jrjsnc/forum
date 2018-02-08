/*
 *authors: Denisa Cekanova, Juraj Senic, Miro Sotak, Tomas Siman
 *project name: Movie forum
 *company: T-systems
 * (c)2018 
 */
package forum.services;

import java.util.List;

import forum.entity.Tag;

// TODO: Auto-generated Javadoc
/**
 * The Interface TagService.
 */
public interface TagService {

	/**
	 * Adds the tag.
	 *
	 * @param tag the tag
	 */
	public void addTag(Tag tag);	
	
	/**
	 * Gets the all tags.
	 *
	 * @return the all tags
	 */
	public List<Tag> getAllTags();
	
	/**
	 * Gets the tag.
	 *
	 * @param ident the ident
	 * @return the tag
	 */
	public  Tag getTag(Long ident);
	
	/**
	 * Delete tag.
	 *
	 * @param tag the tag
	 */
	public void deleteTag(Tag tag);
	
	/**
	 * Update tag.
	 *
	 * @param ident the ident
	 * @param name the name
	 */
	public void updateTag(Long ident, String name);
}
