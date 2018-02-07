package server.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import forum.entity.ForumUser;
import forum.entity.Restriction;
import forum.entity.Tag;
import forum.entity.Topic;
import forum.services.TagService;
import forum.services.TopicService;
import forum.services.UserService;
import forum.services.impl.MailService;

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

	@Autowired
	protected MailService mailService;

	private ForumUser loggedUser;
	
	protected void sendMailInThread(String to, String subject, String messageText) {
		ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
		emailExecutor.execute(new Runnable() {
			@Override
			public void run() {
				mailService.sendMail(to, subject, messageText);
			}
		});
		emailExecutor.shutdown();
	}

	@RequestMapping("/mailLogin")
	public String mailLogin(ForumUser user, Model model) {
		model.addAttribute("message", "");		
		ForumUser fu = userService.getUserByEmail(user.getEmail());

		if (null != fu) {
			StringBuilder sb = new StringBuilder();
			sb.append("You have requested to resend your login information.");
			sb.append(System.lineSeparator());
			sb.append(System.lineSeparator());
			sb.append("username: " + fu.getLogin());
			sb.append(System.lineSeparator());
			sb.append("password: " + fu.getPassword());
			sb.append(System.lineSeparator());

			sendMailInThread(fu.getEmail(), "Movie Forum Login", sb.toString());

			model.addAttribute("message", "Your login information was sent. Check your mailbox.");
			return "login";
		} else {
			model.addAttribute("message", "Incorrect email.");
			return "login";
		}
	}

	@RequestMapping("/")
	public String index(Model model) {
		List<Topic> topics = topicService.getTopics();
		topics.sort((Topic t1, Topic t2) -> t2.getComments().size() - t1.getComments().size());
		model.addAttribute("message", "");
		model.addAttribute("selectedTag", "All");

		model.addAttribute("topics", topics);
		model.addAttribute("tags", tagService.getAllTags());

		return "index";
	}

	@RequestMapping("/filterTopics")
	public String filterTopics(Tag tag, Model model) {
		if (tag.getIdent() == -1) {
			model.addAttribute("tags", tagService.getAllTags());
			model.addAttribute("topics", topicService.getTopics());
			model.addAttribute("selectedTag", "All");
			return "index";
		}
		List<Topic> topics = topicService.getTopics();
		topics = topics.stream().filter(t -> t.getTags().contains(tag)).collect(Collectors.toList());

		model.addAttribute("selectedTag", tagService.getTag(tag.getIdent()).getName());
		model.addAttribute("tags", tagService.getAllTags());
		model.addAttribute("topics", topics);
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
			if (getLoggedUser().getRestriction() == Restriction.BANNED) {
				model.addAttribute("message", "You are banned from this forum. Contact admin to unban.");
				return "login";
			}
			model.addAttribute("message", "");
			model.addAttribute("selectedTag", "All");
			model.addAttribute("topics", topicService.getTopics());
			model.addAttribute("tags", tagService.getAllTags());
			return "index";
		}

		model.addAttribute("message", "Wrong login or password");
		return "login";
	}

	
	
	@RequestMapping("/userProfile")
	public String updateProfile(ForumUser user,Model model) {

		if (!isLogged())
			return "login";
		model.addAttribute("userController", this);
		return "userProfile";
	}
	
	
	@RequestMapping("/updateUser")
	public String updateUser(@RequestParam("file") MultipartFile file, ForumUser user,Model model) {
				
		System.err.println(user.getIdent());
		System.err.println(user.getLogin());
		System.err.println(user.getPassword());
		
		if (!userService.nameTaken(user.getLogin())) {
			user.setRestriction(Restriction.BASIC);
			
			userService.updateUser(user.getIdent(), user.getLogin(), user.getEmail(), file);					
			loggedUser = userService.login(user.getLogin(), loggedUser.getPassword());
			
		}	
		return "index";
	}

	@RequestMapping("/register")
	public String register(@RequestParam("file") MultipartFile file, ForumUser user, Model model) {
		user.setRestriction(Restriction.BASIC);

		if (file != null && !file.isEmpty()) {
			try {
				user.setUserImage(file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			userService.register(user);

			StringBuilder sb = new StringBuilder();
			sb.append("You have successfully registered at our movie forum.");
			sb.append(System.lineSeparator());
			sb.append(System.lineSeparator());
			sb.append("username: " + user.getLogin());
			sb.append(System.lineSeparator());
			sb.append("password: " + user.getPassword());
			sb.append(System.lineSeparator());
			sb.append(System.lineSeparator());
			sb.append("In case you forgot your password, you can contact our admin at this email (movieforum@azet.sk)");	
			
			sendMailInThread(user.getEmail(), "Movie Forum Registration", sb.toString());

			loggedUser = userService.login(user.getLogin(), user.getPassword());
			model.addAttribute("message", "");
			model.addAttribute("selectedTag", "All");
			model.addAttribute("tags", tagService.getAllTags());
			model.addAttribute("topics", topicService.getTopics());
		} catch (DataIntegrityViolationException e) {
			model.addAttribute("message", "Name or email already used. Try another name.");
			return "login";
		}
		return "index";
	}

	@RequestMapping("/logout")
	public String login(Model model) {
		loggedUser = null;
		model.addAttribute("selectedTag", "All");
		model.addAttribute("topics", topicService.getTopics());
		model.addAttribute("tags", tagService.getAllTags());
		return "index";
	}

	public ForumUser getLoggedUser() {
		return loggedUser;
	}

	public boolean isLogged() {
		return loggedUser != null;
	}

	public String decodeToImage(String login) {
		String finalImage = "";
		BufferedImage image;

		try {
			byte[] imageInByteArray = userService.getImage(login);
			if (imageInByteArray != null) {
				ByteArrayInputStream bis = new ByteArrayInputStream(imageInByteArray);
				image = ImageIO.read(bis);
				bis.close();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(image, "png", baos);
				baos.flush();
				imageInByteArray = baos.toByteArray();
				baos.close();
				finalImage = javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
			} else {
				return "";
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException f) {
			f.printStackTrace();
		}

		return "data:image/png;base64," + finalImage;
	}

}
