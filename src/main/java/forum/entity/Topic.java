package forum.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "Topic")
@Table(name = "topic")
public class Topic {
	
	@Id
	@GeneratedValue
	private Long ident;
	
	private String title;
	
	@OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<Comment>();
	
	
	public void addComment(Comment comment) {
		comments.add(comment);
		comment.setTopic(this);
	}
	
	public void removeComment(Comment comment) {
		comments.remove(comment);
		comment.setTopic(null);
	}
	
	public Topic(String title) {
		super();
		this.title = title;		
	}
	
	public Topic() {		
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
