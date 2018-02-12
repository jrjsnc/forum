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

import forum.entity.Comment;
import forum.server.ForumServerTest;
import forum.services.CommentService;
import server.ForumServer;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ContextConfiguration(classes = ForumServerTest.class)
public class CommentServiceTest {
	
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private CommentService commentService;
	
	@Test
	public void addComentTest() {
		final Comment comment = new Comment();		
		comment.setContent("macka");
		
		commentService.addComment(comment);
		final Long id = comment.getIdent();
		
		Assert.assertNotNull(id);
		Assert.assertEquals("macka", commentService.getComment(id).getContent());
	}
	
	@Test
	public void updateCommentTest() {
		final Comment comment1 = new Comment();
		comment1.setContent("macka");
	
		entityManager.persist(comment1);
		
		commentService.updateComment(comment1.getIdent(), "pes");
		
		Assert.assertEquals(commentService.getComment(comment1.getIdent()).getContent(), "pes");
		
	}
	
}