/*
 *authors: Denisa Cekanova, Juraj Senic, Miro Sotak, Tomas Siman
 *project name: Movie forum
 *company: T-systems
 * (c)2018 
 */
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

/**
 * The Class UserController. This class implements features connected with user
 * login, register, profile and others
 * 
 */
@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {

	/** The userService. Object of class UserService. */
	@Autowired
	protected UserService userService;

	/** The topicService. Object of class TopicService. */
	@Autowired
	protected TopicService topicService;

	/** The tagService. Object of class TagService. */
	@Autowired
	protected TagService tagService;

	/** The mailService. Object of class MailService. */
	@Autowired
	protected MailService mailService;

	/** The loggedUser. Object of class ForumUser. */
	private ForumUser loggedUser;

	/**
	 * This method fills the model with data from database. In drop down menu for
	 * tags is always as first tag selected "All".
	 *
	 * @param model
	 */
	private void fillModel(Model model) {
		model.addAttribute("message", "");
		model.addAttribute("selectedTag", "All");
		model.addAttribute("topics", getUnarchivedTopics());
		model.addAttribute("tags", tagService.getAllTags());
	}

	/**
	 * This method gets list of not archived topics.
	 *
	 * @return topics that are not archived.
	 */
	private List<Topic> getUnarchivedTopics() {
		List<Topic> topics = topicService.getTopics();
		Tag tag = tagService.getTagByName("archived");
		topics = topics.stream().filter(t -> !t.getTags().contains(tag)).collect(Collectors.toList());
		topics.sort((Topic t1, Topic t2) -> t2.getComments().size() - t1.getComments().size());
		return topics;
	}

	/**
	 * This method sends mail with parameters to (to whom is mail send), subject
	 * (subject of email), messageText (content of email).
	 *
	 * @param to
	 * @param subject
	 * @param messageText
	 */
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

	/**
	 * This method creates mail by StringBuilder that is send to user in case user
	 * forgot login information. User enter a mail address into form and in case of
	 * correct mail address, information is sent to current email.
	 *
	 * @param user
	 * @param model
	 * @return login.html as string in case of wrong mail (mail that is not in
	 *         database), also in case of correct email(model is filled with message
	 *         of mail sending).
	 */
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

	/**
	 * This method return user to main page of forum and also fill model with data
	 * from database (topics and drop down menu with tags).
	 *
	 * @param model
	 * @return index.html as string (main page of forum)
	 */
	@RequestMapping("/")
	public String index(Model model) {

		List<Topic> topics = topicService.getTopics();
		topics.sort((Topic t1, Topic t2) -> t2.getComments().size() - t1.getComments().size());
		model.addAttribute("message", "");
		model.addAttribute("selectedTag", "All");
		model.addAttribute("topics", topics);
		model.addAttribute("tags", tagService.getAllTags());
		fillModel(model);

		return "index";
	}

	/**
	 * This method filter the topics by selected tags.
	 *
	 * @param tag
	 * @param model
	 * @return index.html as string (main page of forum)
	 */
	@RequestMapping("/filterTopics")
	public String filterTopics(Tag tag, Model model) {

		if (tag.getIdent() == -1) {
			model.addAttribute("tags", tagService.getAllTags());
			model.addAttribute("topics", getUnarchivedTopics());
			model.addAttribute("selectedTag", "All");
			return "index";
		}

		if (tag.getIdent().equals(tagService.getTagByName("archived").getIdent())) {
			List<Topic> topics = topicService.getTopics();
			topics = topics.stream().filter(t -> t.getTags().contains(tag)).collect(Collectors.toList());
			model.addAttribute("topics", topics);
			model.addAttribute("tags", tagService.getAllTags());
			return "index";
		}

		List<Topic> topics = getUnarchivedTopics();
		topics = topics.stream().filter(t -> t.getTags().contains(tag)).collect(Collectors.toList());
		model.addAttribute("selectedTag", tagService.getTag(tag.getIdent()).getName());
		model.addAttribute("topics", topics);
		model.addAttribute("tags", tagService.getAllTags());
		return "index";
	}

	/**
	 * This method defines mapping /user in url .
	 *
	 * @param model
	 * @return login.html as string (web page for login or register of user)
	 */
	@RequestMapping("/user")
	public String user(Model model) {
		return "login";
	}

	/**
	 * This method defines mapping /login in url and redirect user into web page
	 * login.html if the user is BANNED.
	 *
	 * @param user
	 * @param model
	 * @return index.html as string (main page of forum)
	 */
	@RequestMapping("/login")
	public String login(ForumUser user, Model model) {
		loggedUser = userService.login(user.getLogin(), user.getPassword());

		if (isLogged()) {
			if (getLoggedUser().getRestriction() == Restriction.BANNED) {
				model.addAttribute("message", "You are banned from this forum. Contact admin to unban.");
				loggedUser = null;
				return "login";
			}
			fillModel(model);
			return "index";
		}

		model.addAttribute("message", "Wrong login or password");
		return "login";
	}

	/**
	 * This method defines mapping /userProfile in url and redirect user to login if
	 * the user is not logged. If user is logged method redirect him to
	 * userProfile.html (web page of user profile).
	 *
	 * @param user
	 * @param model
	 * @return userProfile.html as string (web page of user profile).
	 */
	@RequestMapping("/userProfile")
	public String updateProfile(ForumUser user, Model model) {

		if (!isLogged())
			return "login";
		model.addAttribute("userController", this);
		return "userProfile";
	}

	/**
	 * This method updates the users information such as login, email, profile
	 * picture and password.
	 *
	 * @param file
	 * @param user
	 * @param model
	 * @return index.html as string after successful update of profile information.
	 */
	@RequestMapping("/updateUser")
	public String updateUser(@RequestParam("file") MultipartFile file, ForumUser user, Model model) {

		

			userService.updateUser(user.getIdent(), user.getLogin(), user.getEmail(), user.getPassword(), file);
			loggedUser = userService.login(user.getLogin(), user.getPassword());
			fillModel(model);
			return "index";
		

	}

	/**
	 * This method defines mapping /register in url and redirect user into main page
	 * of forum in case of successful registration. In case of wrong registration,
	 * user is indirect into logi.html to try registration again.
	 *
	 * @param file
	 * @param user
	 * @param model
	 * @return index.html as string when registration is successful. login.html as
	 *         string if registration is wrong.
	 */
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

	/**
	 * This method defines mapping /logout in url and redirect user into index.html
	 * after logout.
	 *
	 * @param model
	 * @return index.html as string
	 */
	@RequestMapping("/logout")
	public String login(Model model) {
		loggedUser = null;
		fillModel(model);
		return "index";
	}

	/**
	 * This method gets the logged user.
	 *
	 * @return the logged user
	 */
	public ForumUser getLoggedUser() {
		return loggedUser;
	}

	/**
	 * This method checks if user is logged.
	 *
	 * @return true, if is logged
	 */
	public boolean isLogged() {
		return loggedUser != null;
	}

	/**
	 * This method deals with image manipulation. Also rewrite type of every image
	 * into png.
	 *
	 * @param login
	 * @return the string of url to image
	 */
	public String decodeToImage(String login) {
		String finalImage = "";
		BufferedImage image;

		try {
			byte[] imageInByteArray = userService.getImage(login);

			ByteArrayInputStream bis = new ByteArrayInputStream(imageInByteArray);
			image = ImageIO.read(bis);
			bis.close();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", baos);
			baos.flush();
			imageInByteArray = baos.toByteArray();
			baos.close();
			finalImage = javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException r) {
			System.out.println("image not found");
		} catch (NullPointerException f) {
			System.out.println("image not found");
		}

		return "data:image/png;base64," + finalImage;
	}
}
