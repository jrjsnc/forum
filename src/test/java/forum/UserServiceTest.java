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

import forum.entity.ForumUser;
import forum.server.ForumServerTest;
import forum.services.UserService;
import server.ForumServer;



@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ContextConfiguration(classes = ForumServer.class)
public class UserServiceTest {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private UserService userService;
	
	@Test
	public void registerTest() {
		final ForumUser user = new ForumUser();
		user.setLogin("macka");
		
		userService.register(user);
		final Long id = user.getIdent();
		
		Assert.assertNotNull(id);
		Assert.assertEquals("macka", userService.getUser(id).getLogin());
	}
	
	
}