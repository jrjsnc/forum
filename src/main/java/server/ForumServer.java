package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import forum.services.CommentService;
import forum.services.TopicService;
import forum.services.UserService;
import forum.services.impl.CommentServiceJPA;
import forum.services.impl.TopicServiceJPA;
import forum.services.impl.UserServiceJPA;


@SpringBootApplication
//@EnableWs
@EntityScan({"forum.entity"})

public class ForumServer {	
	public static void main(String[] args) {
	      SpringApplication.run(ForumServer.class, args);
	  }
	
	@Bean
	public CommentService commentService() {
		return new CommentServiceJPA();
	}
	
	@Bean
	public TopicService topicService() {
		return new TopicServiceJPA();
	}
	
	@Bean
	public UserService userService() {
		return new UserServiceJPA();
	}

}
