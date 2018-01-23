package server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import forum.entity.ForumUser;
import forum.services.UserService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {

	@Autowired
	private UserService userService;
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
		if (isLogged()) {
			//model.addAttribute("favouriteGames", gameService.getFavouriteGames(getLoggedPlayer().getLogin()));
		}

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

	public ForumUser getLoggedUser() {
		return loggedUser;
	}

	public boolean isLogged() {
		return loggedUser != null;
	}

}
