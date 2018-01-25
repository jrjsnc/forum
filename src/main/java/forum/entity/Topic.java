package forum.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Topic {

	@Id
	@GeneratedValue
	private int ident;
	private String title;
	private String forumUser;
	

	public Topic(String title, String user) {
		super();
		this.title = title;
		this.forumUser = user;
	}
	
	public Topic() {
		
	}

	public String getForumUser() {
		return forumUser;
	}

	public void setForumUser(String forumUser) {
		this.forumUser = forumUser;
	}


	public int getIdent() {
		return ident;
	}

	public void setIdent(int ident) {
		this.ident = ident;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
