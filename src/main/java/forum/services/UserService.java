package forum.services;

import java.util.List;

import forum.entity.ForumUser;
import forum.entity.Restriction;

public interface UserService {
	
	void register(ForumUser user);	
	
	ForumUser login(String login, String password);	
	
	List<ForumUser> getUsers();
	
	boolean nameTaken(String login);
	
	ForumUser getUser(Long ident);

	void setRestriction(Long ident, Restriction restriction);
	


}
