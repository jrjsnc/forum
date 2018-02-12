/*
 *authors: Denisa Cekanova, Juraj Senic, Miro Sotak, Tomas Siman
 *project name: Movie forum
 *company: T-systems
 * (c)2018 
 */
package forum.services;

import java.util.List;

import forum.entity.Tag;


/**
 * The Interface TagService.
 */
public interface TagService {

	/**
	 * This method adds the tag.
	 *
	 * @param tag the tag
	 */
	public void addTag(Tag tag);	
	
	/**
	 * This method gets all tags.
	 *
	 * @return the all tags
	 */
	public List<Tag> getAllTags();
	
	/**
	 * This method gets the tag.
	 *
	 * @param ident the ident
	 * @return the tag
	 */
	public  Tag getTag(Long ident);
	
	/**
	 * This method deletes the tag.
	 *
	 * @param tag the tag
	 */
	public void deleteTag(Tag tag);
	
	/**
	 * This method updates the tag.
	 *
	 * @param ident the ident
	 * @param name the name
	 */
	public void updateTag(Long ident, String name);
}
