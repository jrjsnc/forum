package forum.services;

import java.util.List;
import forum.entity.Topic;


public interface TopicService {

	List<Topic> getTopics(String name);

	void addTopic (Topic topic);
	
	List<Topic> deleteTopics (Topic topic);
}
