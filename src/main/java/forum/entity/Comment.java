/*
 *authors: Denisa Cekanova, Juraj Senic, Miro Sotak, Tomas Siman
 *project name: Movie forum
 *company: T-systems
 * (c)2018 
 */



package forum.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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



/**
 * The Class Comment.
 */
@Entity(name = "Comment")
@Table(name = "comment")
public class Comment {

	/** The ident of each comment. Ident is generated automatically and is unique. */
	@Id
	@GeneratedValue
	private Long ident;
	
	/** The content of each comment. */
	private String content;	
	
	/** The date of created of comment. */
	private Date createdOn;	
	
	/** Many to one relation with entity topic. Foreign key is ident of topic. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey(name = "topic_ident", value = ConstraintMode.NO_CONSTRAINT))	
	private Topic topic;
	
	/** Many to one relation with entity ForumUser. Foreign key is ident of user. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey(name = "forum_user_ident", value = ConstraintMode.NO_CONSTRAINT))	
	private ForumUser forumUser;	
	
	/** Many to many relation for comment likes. One comment can have more likes and one user can get likes to more comments. */
	@ManyToMany(mappedBy = "liked")
	private Set<ForumUser> likers = new HashSet<ForumUser>();
	
	/* method equals. Return true when ident of object equals with ident of comment. 
	 */
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof Comment)) return false;
		return ident != null && ident.equals(((Comment) o).getIdent());
	}
	
	/* return the hashCode of comment.
	 */
	@Override
    public int hashCode() {
        return 33;
    }	
	
	/**
	 * Constructor of two parameters.
	 *
	 * @param content. 
	 * Content of comment 
	 * @param createdOn.
	 * Date of comment
	 */
	public Comment(String content, Date createdOn) {
		super();		
		this.content = content;
		this.createdOn = createdOn;		
	}
	
	/**
	 * Empty constructor.
	 */
	public Comment() {

	}	
	
	/**
	 * Gets the users who likes the comment.
	 *
	 * @return the Set of users who likes the comment.
	 */
	public Set<ForumUser> getLikers() {
		return likers;
	}
	
	/**
	 * Sets the users who likes the comment.
	 *
	 * @param likers the likers
	 */
	public void setLikers(Set<ForumUser> likers) {
		this.likers = likers;
	}
	
	
	/**
	 * Gets the forum user from entity ForumUser.
	 *
	 * @return the forum user from entity ForumUser
	 */
	public ForumUser getForumUser() {
		return forumUser;
	}
	
	/**
	 * Sets the forum user from entity ForumUser.
	 *
	 * @param forumUser the forumUser
	 */
	public void setForumUser(ForumUser forumUser) {
		this.forumUser = forumUser;
	}

	/**
	 * Gets the ident of comment.
	 *
	 * @return the current ident of comment.
	 */
	public Long getIdent() {
		return ident;
	}
	
	/**
	 * Sets the ident of comment.
	 *
	 * @param ident of comment to set.
	 */
	public void setIdent(Long ident) {
		this.ident = ident;
	}	
	
	/**
	 * Gets the topic that is commented by current comment.
	 *
	 * @return the topic.
	 */
	public Topic getTopic() {
		return topic;
	}
	
	/**
	 * Sets the topic that is commented by current comment.
	 *
	 * @param topic to set.
	 */
	public void setTopic(Topic topic) {
		this.topic = topic;
	}	

	/**
	 * Gets the content of comment.
	 *
	 * @return the content of comment.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content of comment.
	 *
	 * @param content of comment to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Gets the date of commented.
	 *
	 * @return the date of commented.
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * Sets the date of commented.
	 *
	 * @param createdOn. The date of create comment to set.
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	/* method toString. Return the string format of comment (ident of comment, user who create comment,
	 *  title of topic for which is comment created, date of create comment).
	 */
	@Override
	public String toString() {
		return String.format("Coment (%d %s %s %tc)", ident, this.getForumUser().getLogin(), topic, createdOn);
	}

}