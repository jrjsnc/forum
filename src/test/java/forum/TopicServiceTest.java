package forum;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import forum.entity.Topic;
import forum.services.TopicService;
import server.ForumServer;


@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ContextConfiguration(classes = ForumServer.class)
public class TopicServiceTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private TopicService topicService;
	
	@Test
	public void addTopicTest() {
		final Topic topic = new Topic();
		topic.setTitle("macka");
		
		topicService.addTopic(topic);
		final Long id = topic.getIdent();
		
		Assert.assertNotNull(id);
		Assert.assertEquals("macka", topicService.getTopic(id).getTitle());
	}
	
}