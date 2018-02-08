package forum;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import forum.entity.Topic;
import forum.services.TopicService;
import forum.services.impl.TopicServiceJPA;


@RunWith(MockitoJUnitRunner.class)
public class TopicServiceTest {

@Mock
private EntityManager entityManager;

@InjectMocks
private TopicService topicService = new TopicServiceJPA();

@Test
public  void addTopicTest() {
	Topic topic = new Topic();
	topicService.addTopic(topic);
	Mockito.verify(entityManager, Mockito.times(1)).persist(topic);
}


}
