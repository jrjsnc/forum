package forum.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "ForumUser")
@Table(name = "forum_user")
public class ForumUser {
	
	@Id
	@GeneratedValue
	private Long ident;
	
	private String login;
	private String password;
	private String email;

	@Enumerated(EnumType.STRING)
	private Restriction restriction;
	
	@OneToMany(mappedBy = "forumUser", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<Comment>();
	
	public void addComment(Comment comment) {
		comments.add(comment);
		comment.setForumUser(this);
	}
	
	public void removeComment(Comment comment) {		
		comments.remove(comment);
		comment.setForumUser(null);
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof ForumUser)) return false;
		return ident != null && ident.equals(((ForumUser) o).getIdent());
	}
	
	@Override
    public int hashCode() {
        return 31;
    }
	
	public Restriction getRestriction() {
		return restriction;
	}
	
	public void setRestriction(Restriction restriction) {
		this.restriction = restriction;
	}
	
	public Long getIdent() {
		return ident;
	}
	public String getLogin() {
		return login;
	}
	public String getPassword() {
		return password;
	}
	public String getEmail() {
		return email;
	}
	public void setIdent(Long ident) {
		this.ident = ident;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return String.valueOf(this.hashCode());		
	}
	
	
	
	
	

}
