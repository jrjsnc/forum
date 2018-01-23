package forum.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "Comment")
@Table(name = "comment")
public class Comment {

	@Id
	@GeneratedValue
	private Long ident;

	private String username;	
	private String content;
	private Date createdOn;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "topic_id")
	private Topic topic;
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof Comment)) return false;
		return ident != null && ident.equals(((Comment) o).ident);
	}
	
	@Override
    public int hashCode() {
        return 31;
    }
	
	
	
	public Comment(String content, Date createdOn) {
		super();
		this.username = "Test user";
		this.content = content;
		this.createdOn = createdOn;
		//this.topic = topic;
	}
	
	public Comment() {

	}	

	public Long getIdent() {
		return ident;
	}
	
	public void setIdent(Long ident) {
		this.ident = ident;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public Topic getTopic() {
		return topic;
	}
	
	public void setTopic(Topic topic) {
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