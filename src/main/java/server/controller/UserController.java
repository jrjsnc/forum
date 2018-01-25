package server.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

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
	

	@RequestMapping("/")
	public String index(Model model) {
		fillModel(model);
		return "index";
	}

	private void fillModel(Model model) {
		// List<Game> games = gameService.getGames();
		// addRatingToGames(games);
		// model.addAttribute("games", games);
		//if (isLogged()) {
			//model.addAttribute("comments", commentService.getComments("comment"));
			model.addAttribute("topics", topicService.getTopics());
			List<String> tags = new ArrayList<String>();
			tags.add("1.tag");
			tags.add("2.tag");
			tags.add("3.tag");
			model.addAttribute("tags", tags);
			
			System.err.println(model.toString());
			//model.addAttribute("favouriteGames", gameService.getFavouriteGames(getLoggedPlayer().getLogin()));
		//}

	}

	@RequestMapping("/user")
	public String user(Model model) {
		return "login";
	}

	@RequestMapping("/login")
	public String login(ForumUser user, Model model) {
		fillModel(model);
		loggedUser = userService.login(user.getLogin(), user.getPassword());
		//if (isLogged()) {
			//model.addAttribute("message", "");
			//fillModel(model);
			return isLogged() ? "index" : "login";
	}

	@RequestMapping("/register")
	public String register(ForumUser user, Model model) {
		try {
			if (!userService.nameTaken(user.getLogin())) {
				user.setRestriction(Restriction.BASIC);
				userService.register(user);
				loggedUser = userService.login(user.getLogin(), user.getPassword());
				model.addAttribute("message", "");
				fillModel(model);
				return "index";
			} else {
			model.addAttribute("message", "Name already used. Try another name.");
			}
			
		} catch (NoResultException e) {
			
		}
		return "login" ;
	}
	
	@RequestMapping("/addTopic")	
	public String topic(@RequestParam(value = "newTopic", required = false) String newTopic, Model model) {
		topicService.addTopic(new Topic(newTopic, getLoggedUser().getLogin()));
		fillModel(model);
		return "index";
	}

	@RequestMapping("/addComment")
	public String comment(@RequestParam(value = "newComment", required = false) String newComment, Model model) {
		commentService.addComment(new Comment(getLoggedUser().getLogin(), "topic", newComment, new Date()));
		fillModel(model);
		return "topic";
	}
	
	
//	@RequestMapping("/deleteComment")
//	public String deleteComment(@RequestParam(value = "ident", required = false)String ident, Model model) {
//		commentService.deleteComment(commentService.getComment(Long.parseLong(ident)));		
//		model.addAttribute("comments", topicService.getTopic(currentTopicIdent).getComments());
//		return "topic";   
//	}
	
	
	
//	@RequestMapping("/addComment2")
//	public String comment2(Comment comment, Model model) {
//		comment.setCreatedOn(new Date());
//		commentService.addComment(comment);
//		fillModel(model);
//		return "index";
//	}
	
	
	
	@RequestMapping("/logout")
	public String login(Model model) {
		loggedUser = null;
		fillModel(model);
		return "index";
	}

	public ForumUser getLoggedUser() {
		return loggedUser;
	}

	public boolean isLogged() {
		return loggedUser != null;
	}
	
	
	

}
