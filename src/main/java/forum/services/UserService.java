package forum.services;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.multipart.MultipartFile;

import forum.entity.Comment;
import forum.entity.ForumUser;
import forum.entity.Restriction;

public interface UserService {
	
	void register(ForumUser user);	
	
	ForumUser login(String login, String password);	
	
	List<ForumUser> getUsers();
	
	boolean nameTaken(String login);
	
	ForumUser getUser(Long ident);
	
	ForumUser getUserByEmail(String email);

	void setRestriction(Long ident, Restriction restriction);

	byte[] getImage(String login);
	
	
	
	@Transactional
	@Modifying
	void deleteUser(ForumUser user);
	
	void updateUser(Long ident, String login, String email, String password, MultipartFile userImage);


	
	void toggleLike(Long ident, Comment comment);
	
	Set<Comment> getLikedComments(Long ident);

	}
