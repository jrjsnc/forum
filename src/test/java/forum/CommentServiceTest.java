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
	public void deleteCommentTest() {
		final Comment comment1 = new Comment();
		comment1.setContent("macka");
		final Comment comment2 = new Comment();
		comment2.setContent("macka2");
		
		entityManager.persist(comment1);
		entityManager.persist(comment2);
		
		// check if comment have id after save
		Assert.assertNotNull(comment1.getIdent());
		Assert.assertNotNull(comment2.getIdent());
		
		// get Comment from DB by entityManager
		Assert.assertNotNull(entityManager.find(Comment.class, 1L));
		Assert.assertNotNull(entityManager.find(Comment.class, 2L));
		
		commentService.deleteComment(comment1);
	
		entityManager.flush();
		entityManager.clear();
		
		// get Comment from DB by entityManager
		final Comment deletedcomment = entityManager.find(Comment.class, 1L);
		Assert.assertNull(deletedcomment);
		final Comment nonDeletedcomment2 = entityManager.find(Comment.class, 2L);
		Assert.assertNotNull(nonDeletedcomment2);
	}
	
}