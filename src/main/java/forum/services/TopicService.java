package forum.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;

import forum.entity.Topic;

public interface TopicService {

	void addTopic(Topic topic);

	List<Topic> getTopics();

	@Transactional
	@Modifying
	void deleteTopic(Topic topic);

	Topic getTopic(Long ident);
}