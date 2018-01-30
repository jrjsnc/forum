package server.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class ForumController {

	@Autowired
	private UserController userController;

	@Autowired
	private CommentService commentService;

	private Long currentTopicIdent;

	private void fillModel(Model model) {
		model.addAttribute("users", userController.userService.getUsers());
		model.addAttribute("tags", userController.tagService.getAllTags());
		if (null != currentTopicIdent) {
			model.addAttribute("currentTopicTitle", userController.topicService.getTopic(currentTopicIdent).getTitle());
			model.addAttribute("comments", userController.topicService.getTopic(currentTopicIdent).getComments());
			model.addAttribute("topicTags", userController.topicService.getTopic(currentTopicIdent).getTags());

			List<Tag> tags = userController.tagService.getAllTags();
			tags.removeAll(userController.topicService.getTopic(currentTopicIdent).getTags());
			model.addAttribute("missingTags", tags);		
		}
	}

	@RequestMapping("/addTag")
	public String addTag(Tag tag, Model model) {
		userController.tagService.addTag(tag);
		model.addAttribute("tags", userController.tagService.getAllTags());
		return "admin";
	}

	@RequestMapping("/addTopicTag")
	public String addTopicTag(Tag tag, Model model) {
		userController.topicService.getTopic(currentTopicIdent)
				.addTag(userController.tagService.getTag(tag.getIdent()));
		fillModel(model);
		return "topic";
	}

	@RequestMapping("/removeTopicTag")
	public String removeTopicTag(Tag tag, Model model) {
		userController.topicService.getTopic(currentTopicIdent)
				.removeTag(userController.tagService.getTag(tag.getIdent()));
		fillModel(model);
		return "topic";
	}
	
	

	@RequestMapping("/topic")
	public String getTopic(@RequestParam(value = "ident", required = false) String ident, Model model) {
		setCurrentTopicIdent(Long.parseLong(ident));
		fillModel(model);		
		return "topic";
	}

	@RequestMapping("/addTopic")
	public String addTopic(Topic topic, Model model) {
		topic.setForumUser(userController.getLoggedUser());
		userController.topicService.addTopic(topic);

		setCurrentTopicIdent(topic.getIdent());
		return "topic";
	}

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

	@RequestMapping("/deleteComment")
	public String deleteComment(@RequestParam(value = "ident", required = false) String ident, Model model) {
		commentService.deleteComment(commentService.getComment(Long.parseLong(ident)));
		model.addAttribute("comments", userController.topicService.getTopic(currentTopicIdent).getComments());
		return "topic";

	}
	
	@RequestMapping("/toggleLike")
	public String toggleLike(@RequestParam(value = "ident", required = false) String ident, Model model) {
		// TODO 
		return "topic";
	}

	@RequestMapping("/toggleAdmin")
	public String toggleAdmin(@RequestParam(value = "ident", required = false) String ident, Model model) {

		switch (userController.userService.getUser(Long.parseLong(ident)).getRestriction()) {
		case BASIC:
			System.err.println(userController.userService.getUser(Long.parseLong(ident)).getRestriction());
			userController.userService.setRestriction(Long.parseLong(ident), Restriction.ADMIN);
			break;
		case ADMIN:
			System.err.println(userController.userService.getUser(Long.parseLong(ident)).getRestriction());
			userController.userService.setRestriction(Long.parseLong(ident), Restriction.BASIC);
			break;
		case BANNED:
			model.addAttribute("message", "Cannot set admin on banned user");
			break;
		}
		fillModel(model);
		return "admin";
	}

	@RequestMapping("/setBan")
	public String toggleBan(@RequestParam(value = "ident", required = false) String ident, Model model) {

		if (userController.userService.getUser(Long.parseLong(ident)).getRestriction() != Restriction.BANNED) {
			userController.userService.setRestriction(Long.parseLong(ident), Restriction.BANNED);
		} else {
			userController.userService.setRestriction(Long.parseLong(ident), Restriction.BASIC);
		}
		fillModel(model);
		return "admin";
	}

	@RequestMapping("/admin")
	public String admin(Model model) {
		fillModel(model);
		return "admin";
	}

	public void setCurrentTopicIdent(Long ident) {
		currentTopicIdent = ident;
	}

}
