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


// TODO: Auto-generated Javadoc
/**
 * The Class Comment.
 */
@Entity(name = "Comment")
@Table(name = "comment")
public class Comment {

	/** The ident. */
	@Id
	@GeneratedValue
	private Long ident;
	
	/** The content. */
	private String content;	
	
	/** The created on. */
	private Date createdOn;	
	
	/** The topic. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey(name = "topic_ident", value = ConstraintMode.NO_CONSTRAINT))	
	private Topic topic;
	
	/** The forum user. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey(name = "forum_user_ident", value = ConstraintMode.NO_CONSTRAINT))	
	private ForumUser forumUser;	
	
	/** The likers. */
	@ManyToMany(mappedBy = "liked")
	private Set<ForumUser> likers = new HashSet<ForumUser>();
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof Comment)) return false;
		return ident != null && ident.equals(((Comment) o).getIdent());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
    public int hashCode() {
        return 33;
    }	
	
	/**
	 * Instantiates a new comment.
	 *
	 * @param content the content
	 * @param createdOn the created on
	 */
	public Comment(String content, Date createdOn) {
		super();		
		this.content = content;
		this.createdOn = createdOn;		
	}
	
	/**
	 * Instantiates a new comment.
	 */
	public Comment() {

	}	
	
	/**
	 * Gets the likers.
	 *
	 * @return the likers
	 */
	public Set<ForumUser> getLikers() {
		return likers;
	}
	
	/**
	 * Sets the likers.
	 *
	 * @param likers the new likers
	 */
	public void setLikers(Set<ForumUser> likers) {
		this.likers = likers;
	}
	
	
	/**
	 * Gets the forum user.
	 *
	 * @return the forum user
	 */
	public ForumUser getForumUser() {
		return forumUser;
	}
	
	/**
	 * Sets the forum user.
	 *
	 * @param forumUser the new forum user
	 */
	public void setForumUser(ForumUser forumUser) {
		this.forumUser = forumUser;
	}

	/**
	 * Gets the ident.
	 *
	 * @return the ident
	 */
	public Long getIdent() {
		return ident;
	}
	
	/**
	 * Sets the ident.
	 *
	 * @param ident the new ident
	 */
	public void setIdent(Long ident) {
		this.ident = ident;
	}	
	
	/**
	 * Gets the topic.
	 *
	 * @return the topic
	 */
	public Topic getTopic() {
		return topic;
	}
	
	/**
	 * Sets the topic.
	 *
	 * @param topic the new topic
	 */
	public void setTopic(Topic topic) {
		this.topic = topic;
	}	

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Gets the created on.
	 *
	 * @return the created on
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * Sets the created on.
	 *
	 * @param createdOn the new created on
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Coment (%d %s %s %tc)", ident, this.getForumUser().getLogin(), topic, createdOn);
	}

}