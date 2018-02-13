package forum;

import java.util.List;

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

import forum.entity.Comment;
import forum.entity.Tag;
import forum.entity.Topic;
import forum.server.ForumServerTest;
import forum.services.TagService;
import server.ForumServer;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ContextConfiguration(classes = ForumServerTest.class)
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
	
	@Test
	public void updateTagTest() {
		final Tag tag = new Tag();
		tag.setName("macka");
	
		entityManager.persist(tag);
		
		tagService.updateTag(tag.getIdent(), "lev");
		
		Assert.assertEquals(tagService.getTag(tag.getIdent()).getName(), "lev");
		
	}
	
	@Test
	public void getTagTest() {
		Tag tag = new Tag();
		tag.setName("macka");
		tagService.addTag(tag);			
		Assert.assertEquals(tag, tagService.getTag(tag.getIdent()));
	}
	
	@Test
	public void getTagsTest() {
		
		Tag tag1 = new Tag();
		tag1.setName("macka");
		entityManager.persist(tag1);		
		
		Tag tag2 = new Tag();
		tag2.setName("pes");
		entityManager.persist(tag2);		
			
		List<Tag> t = tagService.getAllTags();
		
		Assert.assertEquals(2, t.size());		
	}
	
	@Test
	public void getTagByNameTest() {
		Tag tag = new Tag();
		tag.setName("macka");
		tagService.addTag(tag);	
		Assert.assertEquals(tag, tagService.getTagByName("macka"));

	}
}