package forum;

import java.util.List;
import java.util.Set;

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

import forum.entity.Comment;
import forum.entity.ForumUser;
import forum.entity.Restriction;
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

	private Long u1Ident;
	private Long u2Ident;
	private Long u3Ident;

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
		u1Ident = u1.getIdent();
		entityManager.persist(u2);
		u2Ident = u2.getIdent();
		entityManager.persist(u3);
		u3Ident = u3.getIdent();

	}

	@Test
	public void loginTest() {
		ForumUser user = userService.login("u1", "pass");
		Assert.assertEquals(ForumUser.class, user.getClass());

		user = userService.login("u9", "pass");
		Assert.assertNull(user);
	}

	@Test
	public void getUsersTest() {

		ForumUser u4 = new ForumUser();
		u4.setLogin("u4");
		u4.setPassword("pass");
		u4.setEmail("u4@email.com");

		entityManager.persist(u4);

		List<ForumUser> fu = userService.getUsers();

		Assert.assertEquals(4, fu.size());
	}

	@Test
	public void getUserTest() {
		ForumUser u4 = new ForumUser();
		u4.setLogin("u4");
		u4.setPassword("pass");
		u4.setEmail("u4@email.com");

		userService.register(u4);
		final Long id = u4.getIdent();

		Assert.assertEquals("u4", userService.getUser(id).getLogin());
	}

	@Test
	public void getUserByEmailTest() {
		ForumUser u1 = userService.getUser(u1Ident);
		Assert.assertEquals(u1, userService.getUserByEmail("u1@email.com"));
	}

	@Test
	public void nameTaken() {
		Assert.assertTrue(userService.nameTaken("u1"));
		Assert.assertFalse(userService.nameTaken("u9"));
	}

	@Test
	public void setRestrictionTest() {
		userService.setRestriction(u2Ident, Restriction.ADMIN);
		Assert.assertTrue(userService.getUser(u2Ident).getRestriction().equals(Restriction.ADMIN));

		userService.setRestriction(u3Ident, Restriction.BANNED);
		Assert.assertTrue(userService.getUser(u3Ident).getRestriction().equals(Restriction.BANNED));
	}

	@Test
	public void registerTest() {
		ForumUser u = new ForumUser();
		u.setLogin("user");
		u.setPassword("pass");
		u.setEmail("user@email.com");

		userService.register(u);
		Long id = u.getIdent();

		Assert.assertNotNull(id);
		Assert.assertEquals("user", userService.getUser(id).getLogin());
	}

	@Test
	public void toggleLikeTest() {
		Comment c1 = new Comment();
		c1.setContent("c1");
		Comment c2 = new Comment();
		c2.setContent("c2");

		entityManager.persist(c1);
		entityManager.persist(c2);

		userService.toggleLike(u1Ident, c1);
		userService.toggleLike(u1Ident, c2);

		Assert.assertTrue(userService.getUser(u1Ident).getLikedComments().contains(c1));
		Assert.assertTrue(userService.getUser(u1Ident).getLikedComments().contains(c2));

		userService.toggleLike(u1Ident, c2);
		Assert.assertFalse(userService.getUser(u1Ident).getLikedComments().contains(c2));
	}

	@Test
	public void getLikedCommentsTest() {
		Comment c1 = new Comment();
		c1.setContent("c1");
		Comment c2 = new Comment();
		c2.setContent("c2");

		entityManager.persist(c1);
		entityManager.persist(c2);

		userService.toggleLike(u1Ident, c1);
		userService.toggleLike(u1Ident, c2);

		Set<Comment> likedComments = userService.getLikedComments(u1Ident);

		Assert.assertFalse(likedComments.isEmpty());
		Assert.assertEquals(2, likedComments.size());
		Assert.assertTrue(likedComments.contains(c1));
		Assert.assertTrue(likedComments.contains(c2));

	}

}