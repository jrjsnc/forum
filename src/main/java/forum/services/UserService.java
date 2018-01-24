package forum.services;

import forum.entity.ForumUser;

public interface UserService {
	
	void register(ForumUser user);	
	
	ForumUser login(String login, String password);
	
	boolean nameTaken(String login);

	

}
