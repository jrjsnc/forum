/*
 *authors: Denisa Cekanova, Juraj Senic, Miro Sotak, Tomas Siman
 *project name: Movie forum
 *company: T-systems
 * (c)2018 
 */
package forum.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class Topic.
 */
@Entity(name = "Topic")
@Table(name = "topic")
public class Topic {
	
	/** The ident. */
	@Id
	@GeneratedValue
	private Long ident;
	
	/** The title. */
	private String title;
	
	/** The comments. */
	@OneToMany(mappedBy = "topic", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<Comment>();
	
	/** The forum user. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey(name = "forum_user_ident", value = ConstraintMode.CONSTRAINT))	
	private ForumUser forumUser;
	
	/** The tags. */
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinTable(name = "topic_tag", joinColumns = @JoinColumn(name = "topic_ident"), inverseJoinColumns = @JoinColumn(name = "tag_ident"))
	private Set<Tag> tags = new HashSet<Tag>();
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof Topic)) return false;
		return ident != null && ident.equals(((Topic) o).getIdent());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
    public int hashCode() {
        return 30;
    }
	
	/**
	 * Instantiates a new topic.
	 *
	 * @param title the title
	 */
	public Topic(String title) {		
		this.title = title;		
	}
	
	/**
	 * Instantiates a new topic.
	 */
	public Topic() {		
	}	
	
	/**
	 * Adds the tag.
	 *
	 * @param tag the tag
	 */
	public void addTag(Tag tag) {
		tags.add(tag);
		tag.getTopics().add(this);
	}
	
	/**
	 * Removes the tag.
	 *
	 * @param tag the tag
	 */
	public void removeTag(Tag tag) {
		tags.remove(tag);
		tag.getTopics().remove(this);
	}
	
	/**
	 * Adds the comment.
	 *
	 * @param comment the comment
	 */
	public void addComment(Comment comment) {
		comments.add(comment);
		comment.setTopic(this);
	}
	
	/**
	 * Removes the comment.
	 *
	 * @param comment the comment
	 */
	public void removeComment(Comment comment) {
		comments.remove(comment);
		comment.setTopic(null);
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
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Gets the comments.
	 *
	 * @return the comments
	 */
	public List<Comment> getComments() {
		return comments;
	}
	
	/**
	 * Sets the comments.
	 *
	 * @param comments the new comments
	 */
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	/**
	 * Gets the tags.
	 *
	 * @return the tags
	 */
	public Set<Tag> getTags() {
		return tags;
	}
	
	/**
	 * Sets the tags.
	 *
	 * @param tags the new tags
	 */
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

}
