package forum.services;

import java.util.List;

import forum.entity.Topic;

public interface TopicService {

	void addTopic(Topic topic);
	
	List<Topic> getTopics();
	
	void deleteTopic(Topic topic);

	Topic getTopic(Long ident);
}