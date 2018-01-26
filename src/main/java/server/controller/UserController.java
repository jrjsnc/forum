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
import forum.entity.Topic;
import forum.services.CommentService;
import forum.services.TopicService;
import forum.services.UserService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {

	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	Date date = new Date();

	@Autowired
	private UserService userService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private TopicService topicService;

	private ForumUser loggedUser;

	private Long currentTopicIdent;

	@RequestMapping("/")
	public String index(Model model) {
		fillModel(model);
		return "index";
	}

	private void fillModel(Model model) {		
		model.addAttribute("topics", topicService.getTopics());
		model.addAttribute("users", userService.getUsers());
		if(null != currentTopicIdent)
		model.addAttribute("comments", topicService.getTopic(currentTopicIdent).getComments());		
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
			fillModel(model);
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
			fillModel(model);
			return "index";
		}
		model.addAttribute("message", "Name already used. Try another name.");
		return "login";
	}

	@RequestMapping("/logout")
	public String login(Model model) {
		loggedUser = null;
		fillModel(model);
		return "index";
	}

	@RequestMapping("/setup")
	public String test(Model model) {
		Topic t = new Topic("ahoj");
		t.addComment(new Comment("test komentar 1", new Date()));
		t.addComment(new Comment("test komentar 2", new Date()));
		t.addComment(new Comment("test komentar 3", new Date()));
		t.addComment(new Comment("test komentar 4", new Date()));
		topicService.addTopic(t);
		return "index";
	}

	@RequestMapping("/topic")
	public String getTopic(@RequestParam(value = "ident", required = false) String ident, Model model) {
		setCurrentTopicIdent(Long.parseLong(ident));		
		model.addAttribute("comments", topicService.getTopic(currentTopicIdent).getComments());
		return "topic";
	}
	
	@RequestMapping("/addComment")
	public String addComment(Comment comment, Model model) {		
		comment.setTopic(topicService.getTopic(currentTopicIdent));
		comment.setUsername(getLoggedUser().getLogin());
		comment.setCreatedOn(new Date());		
		topicService.getTopic(currentTopicIdent).addComment(comment);		
		//commentService.addComment(comment);		
		fillModel(model);
		model.addAttribute("comments", topicService.getTopic(currentTopicIdent).getComments());
		return "topic";
	}
	
	@RequestMapping("/addTopic")
	public String addTopic(Topic topic, Model model) {
		topicService.addTopic(topic);
		setCurrentTopicIdent(topic.getIdent());
		return "topic";
	}

	@RequestMapping("/deleteComment")
	public String deleteComment(@RequestParam(value = "ident", required = false) String ident, Model model) {
		commentService.deleteComment(commentService.getComment(Long.parseLong(ident)));
		model.addAttribute("comments", topicService.getTopic(currentTopicIdent).getComments());
		return "topic";
<<<<<<< HEAD
	}
=======
	}	
>>>>>>> 15c83994fba2d19f6ea155ac9e50c5f0bd058e75

	@RequestMapping("/setAdmin")
	public String updateRestriction(@RequestParam(value = "ident", required = false) String ident, Model model) {

		switch (userService.getUser(Long.parseLong(ident)).getRestriction()) {

		case BASIC:
			System.err.println(userService.getUser(Long.parseLong(ident)).getRestriction());
			userService.setRestriction(Long.parseLong(ident), Restriction.ADMIN);
			break;
		case ADMIN:
			System.err.println(userService.getUser(Long.parseLong(ident)).getRestriction());
			userService.setRestriction(Long.parseLong(ident), Restriction.BASIC);
			break;
		case BANNED:
			model.addAttribute("message", "Cannot set admin on banned user");
			break;
		}

		fillModel(model);
		return "admin";
	}

	@RequestMapping("/addComment")
	public String comment(Comment comment, Model model) {
		
		comment.setUsername(getLoggedUser();
		comment.setTopic(topic);
		commentService.addComment(new Comment(getLoggedUser().getLogin(), "topic", newComment, new Date()));
		fillModel(model);
		return "topic";
	}

	@RequestMapping("/addTopic")
	public String topic(@RequestParam(value = "newTopic", required = false) String newTopic, Model model) {
		topicService.addTopic(new Topic(newTopic, getLoggedUser().getLogin()));
		fillModel(model);
		return "index";
	}

	@RequestMapping("/admin")
	public String admin(Model model) {
		fillModel(model);
		return "admin";
	}

	public ForumUser getLoggedUser() {
		return loggedUser;
	}

	public boolean isLogged() {
		return loggedUser != null;
	}

	public void setCurrentTopicIdent(Long ident) {
		currentTopicIdent = ident;
	}
}
