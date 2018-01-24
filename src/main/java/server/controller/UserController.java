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
		// List<Game> games = gameService.getGames();
		// addRatingToGames(games);
		// model.addAttribute("games", games);
		model.addAttribute("topics", topicService.getTopics());
		//if (isLogged()) {
			//model.addAttribute("comments", commentService.getComments("comment"));
		
			//model.addAttribute("favouriteGames", gameService.getFavouriteGames(getLoggedPlayer().getLogin()));
		//}

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
	
	@RequestMapping("/test")
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
	public String getTopic(@RequestParam(value = "ident", required = false)String ident, Model model) {
		setCurrentTopicIdent(Long.parseLong(ident));		
		model.addAttribute("comments", topicService.getTopic(currentTopicIdent).getComments());				
		return "topic";
	}
	
	@RequestMapping("/deleteComment")
	public String deleteComment(@RequestParam(value = "ident", required = false)String ident, Model model) {
		commentService.deleteComment(commentService.getComment(Long.parseLong(ident)));		
		model.addAttribute("comments", topicService.getTopic(currentTopicIdent).getComments());
		return "topic";
	}
	
	@RequestMapping("/deleteTopic")
	public String deleteTopic(@RequestParam(value = "ident", required = false)String ident, Model model) {
		Topic t = topicService.getTopic(Long.parseLong(ident));		
		
		System.err.println(t.getComments().toString());
		topicService.deleteTopic(t);
		return "index";
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
