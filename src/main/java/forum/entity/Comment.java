package forum.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "Comment")
@Table(name = "comment")
public class Comment {

	@Id
	@GeneratedValue
	private Long ident;
	
	private String content;	
	private Date createdOn;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey(name = "topic_ident", value = ConstraintMode.NO_CONSTRAINT))	
	private Topic topic;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey(name = "forum_user_ident", value = ConstraintMode.NO_CONSTRAINT))	
	private ForumUser forumUser;	
	
	@ManyToMany(mappedBy = "liked")
	private Set<ForumUser> likers = new HashSet<ForumUser>();
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof Comment)) return false;
		return ident != null && ident.equals(((Comment) o).getIdent());
	}
	
	@Override
    public int hashCode() {
        return 33;
    }	
	
	public Comment(String content, Date createdOn) {
		super();		
		this.content = content;
		this.createdOn = createdOn;		
	}
	
	public Set<ForumUser> getLikers() {
		return likers;
	}
	
	public void setLikers(Set<ForumUser> likers) {
		this.likers = likers;
	}
	
	public Comment() {

	}	
	
	public ForumUser getForumUser() {
		return forumUser;
	}
	
	public void setForumUser(ForumUser forumUser) {
		this.forumUser = forumUser;
	}

	public Long getIdent() {
		return ident;
	}
	
	public void setIdent(Long ident) {
		this.ident = ident;
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
		return String.format("Coment (%d %s %s %tc)", ident, this.getForumUser().getLogin(), topic, createdOn);
	}

}