package server.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
		List<Topic> topics = topicService.getTopics();		
		topics.sort((Topic t1, Topic t2)-> t2.getComments().size() - t1.getComments().size());		

		model.addAttribute("topics", topics);
		model.addAttribute("tags", tagService.getAllTags());		
		return "index";
	}

	@RequestMapping("/filterTopics")
	public String filterTopics(Tag tag, Model model) {
		if(tag.getIdent() == -1) {
			model.addAttribute("tags", tagService.getAllTags());
			model.addAttribute("topics", topicService.getTopics());
			return "index";
		}
		List<Topic> topics = topicService.getTopics();
		topics = topics.stream().filter(t -> t.getTags().contains(tag)).collect(Collectors.toList());		
		
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
			model.addAttribute("message", "");
			model.addAttribute("topics", topicService.getTopics());
			return "index";
		}
		model.addAttribute("message", "Wrong login or password");
		return "login";
	}

	@RequestMapping("/register")
	public String register(@RequestParam("file") MultipartFile file,ForumUser user, Model model) {
		if (!userService.nameTaken(user.getLogin())) {
			user.setRestriction(Restriction.BASIC);
			
			if (file != null && !file.isEmpty()) {
				try {
					user.setUserImage(file.getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
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
