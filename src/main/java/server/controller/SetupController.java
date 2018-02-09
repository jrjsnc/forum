package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import forum.entity.ForumUser;
import forum.entity.Restriction;
import forum.entity.Tag;
import forum.services.TagService;
import forum.services.UserService;

@Controller
public class SetupController {

	@Autowired
	protected UserService userService;
	
	@Autowired
	protected TagService tagService;
	
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
