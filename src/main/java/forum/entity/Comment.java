package forum.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Comment {

	@Id
	@GeneratedValue
	private long ident;

	
	private String username;
	private String topic;
	private String content;
	private Date createdOn;
	
	public Comment() {

	}
	
	public Comment(String username, String topic, String content, Date createdOn) {
		super();
		this.username = username;
		this.topic = topic;
		this.content = content;
		this.createdOn = createdOn;
	}

	public long getIdent() {
		return ident;
	}

	public void setIdent(long ident) {
		this.ident = ident;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		return String.format("Coment (%d %s %s %s %tc)", ident, username, topic, content, createdOn);
	}

}