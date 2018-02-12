/*
 *authors: Denisa Cekanova, Juraj Senic, Miro Sotak, Tomas Siman
 *project name: Movie forum
 *company: T-systems
 * (c)2018 
 */
package server;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import forum.services.CommentService;
import forum.services.TagService;
import forum.services.TopicService;
import forum.services.UserService;
import forum.services.impl.CommentServiceJPA;
import forum.services.impl.MailService;
import forum.services.impl.TagServiceJPA;
import forum.services.impl.TopicServiceJPA;
import forum.services.impl.UserServiceJPA;
import server.controller.SetupController;

// TODO: Auto-generated Javadoc
/**
 * The Class ForumServer.
 */
@SpringBootApplication
// @EnableWs
@EntityScan({ "forum.entity" })
public class ForumServer {

	@Autowired
	private SetupController setupController;

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(ForumServer.class, args);
	}

	/**
	 * Comment service.
	 *
	 * @return the comment service
	 */
	@Bean
	public CommentService commentService() {
		return new CommentServiceJPA();
	}

	/**
	 * Topic service.
	 *
	 * @return the topic service
	 */
	@Bean
	public TopicService topicService() {
		return new TopicServiceJPA();
	}

	/**
	 * User service.
	 *
	 * @return the user service
	 */
	@Bean
	public UserService userService() {
		return new UserServiceJPA();
	}

	/**
	 * Tag service.
	 *
	 * @return the tag service
	 */
	@Bean
	public TagService tagService() {
		return new TagServiceJPA();
	}

	/**
	 * Mail service.
	 *
	 * @return the mail service
	 */
	@Bean
	public MailService mailService() {
		return new MailService();
	}

	@PostConstruct
	public void addAdmin() {
		setupController.addAdmin();
		setupController.addArchivedTag();
	}

}
