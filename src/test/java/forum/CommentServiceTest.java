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
import forum.entity.Topic;
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
		Comment comment = new Comment();		
		comment.setContent("macka");
		
		commentService.addComment(comment);
		final Long id = comment.getIdent();
		
		Assert.assertNotNull(id);
		Assert.assertEquals("macka", commentService.getComment(id).getContent());
	}
	
	@Test
	public void updateCommentTest() {
		Comment comment1 = new Comment();
		comment1.setContent("macka");
	
		entityManager.persist(comment1);
		
		commentService.updateComment(comment1.getIdent(), "pes");
		
		Assert.assertEquals(commentService.getComment(comment1.getIdent()).getContent(), "pes");
		
	}
	@Test
	public void getCommentTest() {
		Comment c1 = new Comment();
		c1.setContent("macka");
		commentService.addComment(c1);			
		Assert.assertEquals(c1, commentService.getComment(c1.getIdent()));
	}
	
	@Test
	public void getCommentsTest() {
		Topic t = new Topic();
		t.setTitle("zvierata");
		
		Comment c1 = new Comment();
		c1.setContent("macka");
		c1.setTopic(t);
		entityManager.persist(c1);		
		
		Comment c2 = new Comment();
		c2.setContent("pes");
		c2.setTopic(t);
		entityManager.persist(c2);		
			
		List<Comment> c = commentService.getComments(t);
		
		Assert.assertEquals(2, c.size());		
	}
}