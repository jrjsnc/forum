package forum.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Comment {

	@Id
	@GeneratedValue
	private int ident;

	private String username;
	private String topic;
	private String content;
	private Date createdOn;
	
	public Comment(String username, String topic, String content, Date createdOn) {
		this.username = username;
		this.topic = topic;
		this.content = content;
		this.createdOn = new Timestamp(createdOn.getTime());
	}

	public Comment() {
	}	

	public int getIdent() {
		return ident;
	}

	public void setIdent(int ident) {
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

	public void setGame(String topic) {
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

}

