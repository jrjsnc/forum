package server.controller;

import org.springframework.beans.factory.annotation.Autowired;

import forum.services.CommentService;
import forum.services.TopicService;



public class ServiceController {
	
	@Autowired
	private UserController userController;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private TopicService topicService;
	
	

}
