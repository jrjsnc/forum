package server.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import forum.services.TagService;
import forum.services.TopicService;
import forum.services.UserService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {

	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	Date date = new Date();

	@Autowired
	protected UserService userService;

	@Autowired
	protected TopicService topicService;
	
	@Autowired
	protected TagService tagService;

	private ForumUser loggedUser;

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("topics", topicService.getTopics());
		model.addAttribute("tags", tagService.getAllTags());
		return "index";
	}

	@RequestMapping("/user")
	public String user(Model model) {
		return "login";
	}

	@RequestMapping("/login")
	public String login(ForumUser user, Model model) {
		loggedUser = userService.login(user.getLogin(), user.getPassword());
		if (isLogged()) {
			model.addAttribute("message", "");
			model.addAttribute("topics", topicService.getTopics());
			return "index";
		}
		return "login";
	}

	@RequestMapping("/register")
	public String register(ForumUser user, Model model) {
		if (!userService.nameTaken(user.getLogin())) {
			user.setRestriction(Restriction.BASIC);
			userService.register(user);
			loggedUser = userService.login(user.getLogin(), user.getPassword());
			model.addAttribute("message", "");
			model.addAttribute("topics", topicService.getTopics());
			return "index";
		}
		model.addAttribute("message", "Name already used. Try another name.");
		return "login";
	}

	@RequestMapping("/logout")
	public String login(Model model) {
		loggedUser = null;
		model.addAttribute("topics", topicService.getTopics());
		return "index";
	}

	// @RequestMapping("/setup")
	// public String test(Model model) {
	// Topic t = new Topic("ahoj");
	// t.addComment(new Comment("test komentar 1", new Date()));
	// t.addComment(new Comment("test komentar 2", new Date()));
	// t.addComment(new Comment("test komentar 3", new Date()));
	// t.addComment(new Comment("test komentar 4", new Date()));
	// topicService.addTopic(t);
	// return "index";
	// }

	// @RequestMapping("/updateTag")

	// @RequestMapping("/deleteTag")

	public ForumUser getLoggedUser() {
		return loggedUser;
	}

	public boolean isLogged() {
		return loggedUser != null;
	}

}
