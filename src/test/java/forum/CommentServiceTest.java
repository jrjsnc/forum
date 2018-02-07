package forum;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import forum.entity.Comment;
import forum.services.CommentService;
import forum.services.impl.CommentServiceJPA;


@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {

@Mock
private EntityManager entityManager;

@InjectMocks
private CommentService commentService = new CommentServiceJPA();

@Test
public  void addCommentTest() {
	Comment comment = new Comment();
	commentService.addComment(comment);
	Mockito.verify(entityManager, Mockito.times(1)).persist(comment);
}


}