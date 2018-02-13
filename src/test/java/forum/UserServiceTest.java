package forum;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import forum.entity.ForumUser;
import forum.server.ForumServerTest;
import forum.services.UserService;
import server.ForumServer;



@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ContextConfiguration(classes = ForumServerTest.class)
public class UserServiceTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private UserService userService;
	
	@Before
	public void generate() {
		ForumUser u1 = new ForumUser();
		u1.setLogin("u1");
		u1.setPassword("pass");
		u1.setEmail("u1@email.com");
		ForumUser u2 = new ForumUser();
		u2.setLogin("u2");
		u2.setPassword("pass");
		u2.setEmail("u2@email.com");
		ForumUser u3 = new ForumUser();
		u3.setLogin("u3");
		u3.setPassword("pass");
		u3.setEmail("u3@email.com");
		
		entityManager.persist(u1);
		entityManager.persist(u2);
		entityManager.persist(u3);
	}
	
	@Test
	public void registerTest() {
		ForumUser u = new ForumUser();
		u.setLogin("user");
		u.setPassword("pass");
		u.setEmail("user@email.com");
		
		userService.register(u);
		final Long id = u.getIdent();
		
		Assert.assertNotNull(id);
		Assert.assertEquals("user", userService.getUser(id).getLogin());
	}
	
	
}