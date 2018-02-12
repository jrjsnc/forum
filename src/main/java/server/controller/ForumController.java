/*
 *authors: Denisa Cekanova, Juraj Senic, Miro Sotak, Tomas Siman
 *project name: Movie forum
 *company: T-systems
 * (c)2018 
 */
package server.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import forum.entity.Comment;
import forum.entity.ForumUser;
import forum.entity.Restriction;
import forum.entity.Tag;
import forum.entity.Topic;
import forum.services.CommentService;



/**
 * The Class ForumController.
 * This class contains all of the important features to control forum. 
 */
@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class ForumController {

	/** The userController. Object of class UserController. */
	@Autowired
	private UserController userController;

	/** The commentService. Object of class CommentService. */
	@Autowired
	private CommentService commentService;

	/** The current topic ident.  */
	private Long currentTopicIdent;

	/**
	 * Method fillModel. This method fills model with datas from database. If currentTopicIdent is not null model is filled with current topic title, 
	 * comments for topics, tags added to each topic and list of tags in drop down menu at index.html
	 *
	 * @param model
	 */
	private void fillModel(Model model) {
		model.addAttribute("controller", this);

		List<ForumUser> users = userController.userService.getUsers();
		users.sort((ForumUser u1, ForumUser u2) -> u1.getLogin().compareToIgnoreCase(u2.getLogin()));
		model.addAttribute("users", users);

		model.addAttribute("tags", userController.tagService.getAllTags());
		if (null != currentTopicIdent) {
			model.addAttribute("currentTopicTitle", userController.topicService.getTopic(currentTopicIdent).getTitle());

			List<Comment> comments = userController.topicService.getTopic(currentTopicIdent).getComments();
			comments.sort((Comment c1, Comment c2) -> c2.getLikers().size() - c1.getLikers().size());
			model.addAttribute("comments", comments);

			model.addAttribute("topicTags", userController.topicService.getTopic(currentTopicIdent).getTags());

			List<Tag> tags = userController.tagService.getAllTags();
			tags.removeAll(userController.topicService.getTopic(currentTopicIdent).getTags());
			model.addAttribute("missingTags", tags);
		}
	}

	/**
	 * Adds the tag. This method allows user with restriction ADMIN adding new tags. Same tag can not be added twice. 
	 * @param tag 
	 * @param model 
	 * @return admin.html as string (web page for admins only where BASIC and BANNED user has no access.)
	 */
	@RequestMapping("/addTag")
	public String addTag(Tag tag, Model model) {
		model.addAttribute("message", "");
		try {
			tag.setName(tag.getName().toLowerCase());
			userController.tagService.addTag(tag);
		} catch (DataIntegrityViolationException e) {
			model.addAttribute("message", "Cannot add the same tag twice.");			
		}		
		model.addAttribute("tags", userController.tagService.getAllTags());
		fillModel(model);
		return "admin";
	}
	
	/**
	 * Update tag. This method allows user with restriction ADMIN update tags. 
	 *
	 * @param tag 
	 * @param model 
	 * @return admin.html as string (web page for admins only where BASIC and BANNED user has no access.)
	 */
	@RequestMapping("/updateTag")
	public String updateTag(Tag tag, Model model) {
		userController.tagService.updateTag(tag.getIdent(), tag.getName());
		model.addAttribute("tags", userController.tagService.getAllTags());
		fillModel(model);		
		return "admin";
	}

	/**
	 * Adds the topic tag. This method allows user with restriction ADMIN assign tag to current topic.
	 * @param tag 
	 * @param model 
	 * @return topic.html as string (web page where user can add comments to current topic)
	 */
	@RequestMapping("/addTopicTag")
	public String addTopicTag(Tag tag, Model model) {
		userController.topicService.getTopic(currentTopicIdent)
				.addTag(userController.tagService.getTag(tag.getIdent()));
		fillModel(model);
		return "topic";
	}

	/**

	 * Removes the topic tag. This method allows user with restriction ADMIN removes tag from topic.
	 *
	 * @param tag 
	 * @param model 
	 * @return topic.html as string (web page where user can add comments to current topic)
	 */
	@RequestMapping("/removeTopicTag")
	public String removeTopicTag(Tag tag, Model model) {
		userController.topicService.getTopic(currentTopicIdent)
				.removeTag(userController.tagService.getTag(tag.getIdent()));
		fillModel(model);
		return "topic";
	}

	/**
	 * Toggle like. This method allows user to like or unlike comment. User can like/unlike comment only once.
	 * @param ident 
	 * @param model 
	 * @return topic.html as string (web page where user can add comments to current topic)
	 */
	@RequestMapping("/toggleLike")
	public String toggleLike(@RequestParam(value = "ident", required = false) String ident, Model model) {
		Comment c = commentService.getComment(Long.parseLong(ident));
		userController.userService.toggleLike(userController.getLoggedUser().getIdent(), c);
		fillModel(model);
		return "topic";
	}

	/**
	 * This method identify if the comment is already liked.
	 *
	 * @param ident 
	 * @return true, if user liked comment or false, if user have not liked comment yet.
	 */
	public boolean haveILiked(Long ident) {
		if (userController.userService.getLikedComments(userController.getLoggedUser().getIdent())
				.contains(commentService.getComment(ident)))
			return true;
		else
			return false;
	}

	/**
	 * This method return web page topic.html where user can add comments to current topic.
	 *
	 * @param ident
	 * @param model
	 * @return topic.html as string (web page where user can add comments to current topic)
	 */
	@RequestMapping("/topic")
	public String getTopic(@RequestParam(value = "ident", required = false) String ident, Model model) {
		setCurrentTopicIdent(Long.parseLong(ident));
		fillModel(model);
		return "topic";
	}

	/**
	 * This method allows user to add new topic.
	 *
	 * @param topic 
	 * @param model 
	 * @return topic.html as string (web page where user can add comments to current topic)
	 */
	@RequestMapping("/addTopic")
	public String addTopic(Topic topic, Model model) {
		topic.setForumUser(userController.getLoggedUser());
		userController.topicService.addTopic(topic);

		setCurrentTopicIdent(topic.getIdent());
		fillModel(model);

		return "topic";
	}

	/**
	 * This method allows user to add comments to topic.
	 *
	 * @param comment
	 * @param model
	 * @return topic.html as string (web page where user can add comments to current topic)
	 */
	@RequestMapping("/addComment")
	public String addComment(Comment comment, Model model) {

		comment.setForumUser(userController.getLoggedUser());
		comment.setTopic(userController.topicService.getTopic(currentTopicIdent));
		comment.setCreatedOn(new Date());

		userController.topicService.getTopic(currentTopicIdent).addComment(comment);
		fillModel(model);
		model.addAttribute("comments", userController.topicService.getTopic(currentTopicIdent).getComments());
		return "topic";
	}

	/**
	 * This method updates the comment.
	 *
	 * @param comment the comment
	 * @param model the model
	 * @return topic.html as string (web page where user can add comments to current topic)
	 */
	@RequestMapping("/updateComment")
	public String updateComment(Comment comment, Model model) {
		commentService.updateComment(comment.getIdent(), comment.getContent());
		fillModel(model);
		return "topic";
	}

	/**
	 * This method allows user with restriction ADMIN to toggle another user (only BASIC) as ADMIN. ADMIN can also change restriction of another
	 * user from ADMIN to BASIC.
	 *
	 * @param ident 
	 * @param model 
	 * @return admin.html as string (web page for admins only where BASIC and BANNED user has no access.)
	 */
	@RequestMapping("/toggleAdmin")
	public String toggleAdmin(@RequestParam(value = "ident", required = false) String ident, Model model) {

		switch (userController.userService.getUser(Long.parseLong(ident)).getRestriction()) {
		case BASIC:
			userController.userService.setRestriction(Long.parseLong(ident), Restriction.ADMIN);
			mailRestriction(userController.userService.getUser(Long.parseLong(ident)).getEmail(), Restriction.ADMIN);
			break;
		case ADMIN:
			userController.userService.setRestriction(Long.parseLong(ident), Restriction.BASIC);
			mailRestriction(userController.userService.getUser(Long.parseLong(ident)).getEmail(), Restriction.BASIC);
			break;
		case BANNED:
			model.addAttribute("message", "Cannot set admin on banned user");
			break;
		}
		fillModel(model);
		return "admin";
	}

	/**
	 * This method allows user with restriction ADMIN to toggle user as BANNED or UNBANNED.
	 *
	 * @param ident
	 * @param model
	 * @return admin.html as string (web page for admins only where BASIC and BANNED user has no access.)
	 */
	@RequestMapping("/toggleBan")
	public String toggleBan(@RequestParam(value = "ident", required = false) String ident, Model model) {
		if (userController.userService.getUser(Long.parseLong(ident)).getRestriction() != Restriction.BANNED) {
			userController.userService.setRestriction(Long.parseLong(ident), Restriction.BANNED);
			mailRestriction(userController.userService.getUser(Long.parseLong(ident)).getEmail(), Restriction.BANNED);
		} else {
			userController.userService.setRestriction(Long.parseLong(ident), Restriction.BASIC);
			mailRestriction(userController.userService.getUser(Long.parseLong(ident)).getEmail(), Restriction.BASIC);
		}
		fillModel(model);
		return "admin";
	}

	/**
	 * This method redirect BASIC or BANNED users to web page 404.html in case they try to penetrate into admin.html
	 *
	 * @param model 
	 * @return 404.html as string if user is BASIC or BANNED. If user is ADMIN return admin.html as string.
	 */
	@RequestMapping("/admin")
	public String admin(Model model) {
		if (!userController.isLogged() || userController.getLoggedUser().getRestriction() != Restriction.ADMIN)
			return "404";
		fillModel(model);
		return "admin";
	}

	/**
	 * This method provide sending email for users when their restriction is change .
	 *
	 * @param email
	 * @param restriction
	 */
	private void mailRestriction(String email, Restriction restriction) {
		String subject = "MovieForum restriction";
		StringBuilder sb = new StringBuilder();
		sb.append("Your profile permissions has been updated to ");
		sb.append(System.lineSeparator());
		sb.append(restriction.toString().toLowerCase());
		sb.append(". For more information contact our admin at this email (movieforum@azet.sk)");
		
		userController.sendMailInThread(email, subject, sb.toString());		
	}

	/**
	 * This method sets the current topic ident.
	 *
	 * @param ident to set.
	 */
	public void setCurrentTopicIdent(Long ident) {
		currentTopicIdent = ident;
	}

}
