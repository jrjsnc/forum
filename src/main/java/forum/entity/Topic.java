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


/**
 * The Class Topic.
 */
@Entity(name = "Topic")
@Table(name = "topic")
public class Topic {
	
	/** The ident of topic. Ident is unique and generated automatically. */
	@Id
	@GeneratedValue
	private Long ident;
	
	/** The title of topic. */
	private String title;
	
	/** The one to many relation with entity Comment. One topic can have more than one comments. One comment can be assign only to one topic. */
	@OneToMany(mappedBy = "topic", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<Comment>();
	
	/** The many to one relation with entity ForunUser. Foreign key is ident of user. One user cad add more topics. 
	 * One topic can be added by only one user. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey(name = "forum_user_ident", value = ConstraintMode.CONSTRAINT))	
	private ForumUser forumUser;
	
	/** The many to many relation with entity Tag. One topic can have more tags and one tag can be added to more topics.*/
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinTable(name = "topic_tag", joinColumns = @JoinColumn(name = "topic_ident"), inverseJoinColumns = @JoinColumn(name = "tag_ident"))
	private Set<Tag> tags = new HashSet<Tag>();
	
	/* method equals. Return true when ident of object equals with ident of topic. 
	 */
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof Topic)) return false;
		return ident != null && ident.equals(((Topic) o).getIdent());
	}
	
	/* return the hashcode of topic.
	 */
	@Override
    public int hashCode() {
        return 30;
    }
	
	/**
	 * Constructor with one parameter.
	 *
	 * @param title of topic.
	 */
	public Topic(String title) {		
		this.title = title;		
	}
	
	/**
	 * Empty constructor.
	 */
	public Topic() {		
	}	
	
	/**
	 * Adds the tag to topic.
	 *
	 * @param tag.
	 */
	public void addTag(Tag tag) {
		tags.add(tag);
		tag.getTopics().add(this);
	}
	
	/**
	 * Removes the tag from topic.
	 *
	 * @param tag.
	 */
	public void removeTag(Tag tag) {
		tags.remove(tag);
		tag.getTopics().remove(this);
	}
	
	/**
	 * Adds the comment to topic.
	 *
	 * @param comment.
	 */
	public void addComment(Comment comment) {
		comments.add(comment);
		comment.setTopic(this);
	}
	
	/**
	 * Removes the comment from topic.
	 *
	 * @param comment.
	 */
	public void removeComment(Comment comment) {
		comments.remove(comment);
		comment.setTopic(null);
	}
	
	/**
	 * Gets the forum user who add the topic.
	 *
	 * @return forumUser.
	 */
	public ForumUser getForumUser() {
		return forumUser;
	}
	
	/**
	 * Sets the forum user who add the topic.
	 *
	 * @param forumUser to set.
	 */
	public void setForumUser(ForumUser forumUser) {
		this.forumUser = forumUser;
	}
	
	
	
	/**
	 * Gets the ident of topic.
	 *
	 * @return ident of topic.
	 */
	public Long getIdent() {
		return ident;
	}
	
	/**
	 * Sets the ident of topic.
	 *
	 * @param ident of topic to set.
	 */
	public void setIdent(Long ident) {
		this.ident = ident;
	}
	
	/**
	 * Gets the topic's title.
	 *
	 * @return title of topic.
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the topic's title.
	 *
	 * @param title of topic to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Gets the topic's comments.
	 *
	 * @return comments of topic.
	 */
	public List<Comment> getComments() {
		return comments;
	}
	
	/**
	 * Sets the topic's comments.
	 *
	 * @param comments of topic to set.
	 */
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	/**
	 * Gets the topic's tags.
	 *
	 * @return tags of topic.
	 */
	public Set<Tag> getTags() {
		return tags;
	}
	
	/**
	 * Sets the topic's tags.
	 *
	 * @param tags of topic to set.
	 */
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

}
