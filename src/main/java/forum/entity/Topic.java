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

@Entity(name = "Topic")
@Table(name = "topic")
public class Topic {
	
	@Id
	@GeneratedValue
	private Long ident;
	
	private String title;
	
	@OneToMany(mappedBy = "topic", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<Comment>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey(name = "forum_user_ident", value = ConstraintMode.NO_CONSTRAINT))	
	private ForumUser forumUser;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "topic_tag", joinColumns = @JoinColumn(name = "topic_ident"), inverseJoinColumns = @JoinColumn(name = "tag_ident"))
	private Set<Tag> tags = new HashSet<>();
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof Topic)) return false;
		return ident != null && ident.equals(((Topic) o).getIdent());
	}
	
	@Override
    public int hashCode() {
        return 30;
    }
	
	public Topic(String title) {		
		this.title = title;		
	}
	
	public Topic() {		
	}	
	
	public void addTag(Tag tag) {
		tags.add(tag);
		tag.getTopics().add(this);
	}
	
	public void removeTag(Tag tag) {
		tags.remove(tag);
		tag.getTopics().remove(this);
	}
	
	public void addComment(Comment comment) {
		comments.add(comment);
		comment.setTopic(this);
	}
	
	public void removeComment(Comment comment) {
		comments.remove(comment);
		comment.setTopic(null);
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public List<Comment> getComments() {
		return comments;
	}
	
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}
