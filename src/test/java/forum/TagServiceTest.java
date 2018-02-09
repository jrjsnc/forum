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

import forum.entity.Tag;
import forum.services.TagService;
import server.ForumServer;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ContextConfiguration(classes = ForumServer.class)
public class TagServiceTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private TagService tagService;
	
	@Test
	public void addTagTest() {
		final Tag tag = new Tag();
		tag.setName("macka");
		
		tagService.addTag(tag);
		final Long id = tag.getIdent();
		
		Assert.assertNotNull(id);
		Assert.assertEquals("macka", tagService.getTag(id).getName());
	}
	
}