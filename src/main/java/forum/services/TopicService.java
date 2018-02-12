/*
 *authors: Denisa Cekanova, Juraj Senic, Miro Sotak, Tomas Siman
 *project name: Movie forum
 *company: T-systems
 * (c)2018 
 */
package forum.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;

import forum.entity.Topic;


/**
 * The Interface TopicService.
 */
public interface TopicService {

	/**
	 * This method adds the topic.
	 *
	 * @param topic the topic
	 */
	void addTopic(Topic topic);

	/**
	 * This method gets the topics.
	 *
	 * @return the topics
	 */
	List<Topic> getTopics();

	/**
	 * This method deletes the topic.
	 *
	 * @param topic the topic
	 */
	@Transactional
	@Modifying
	void deleteTopic(Topic topic);

	/**
	 * This method gets the topic.
	 *
	 * @param ident the ident
	 * @return the topic
	 */
	Topic getTopic(Long ident);
}