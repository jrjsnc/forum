/*
 *authors: Denisa Cekanova, Juraj Senic, Miro Sotak, Tomas Siman
 *project name: Movie forum
 *company: T-systems
 * (c)2018 
 */
package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import forum.entity.ForumUser;
import forum.entity.Restriction;
import forum.entity.Tag;
import forum.services.TagService;
import forum.services.UserService;


/**
 * The Class SetupController.
 */
@Controller
public class SetupController {

	/** The user service. */
	@Autowired
	protected UserService userService;
	
	/** The tag service. */
	@Autowired
	protected TagService tagService;
	
	/**
	 * Adds the admin.
	 */
	public void addAdmin() {		
		ForumUser user = new ForumUser();
		user.setLogin("admin");
		user.setPassword("admin");
		user.setEmail("movieforum@zoznam.sk");
		user.setRestriction(Restriction.ADMIN);
		try {
			userService.register(user);
		} catch (DataIntegrityViolationException e) {			
			System.out.println("Admin user already in db");
		}		
	}
	
	/**
	 * Adds the archived tag.
	 */
	public void addArchivedTag() {
		Tag tag = new Tag();
		tag.setName("archived");
		try {
			tagService.addTag(tag);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Archived tag already in db");
		}
	}
}
