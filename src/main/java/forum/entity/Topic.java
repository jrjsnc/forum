package forum.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Topic {

	@Id
	@GeneratedValue
	private long ident;
	private String title;
	private String forumUser;

	public Topic() {

	}

	public Topic(String title, String user) {
		super();
		this.title = title;
		this.forumUser = user;
	}

	public String getForumUser() {
		return forumUser;
	}

	public void setForumUser(String forumUser) {
		this.forumUser = forumUser;
	}

	public long getIdent() {
		return ident;
	}

	public void setIdent(long ident) {
		this.ident = ident;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
