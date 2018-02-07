package forum.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "ForumUser")
@Table(name = "forum_user")
public class ForumUser {

	@Id
	@GeneratedValue
	private Long ident;

	@Column(unique=true, nullable=false)
	private String login;
	
	@Column(nullable=false)
	private String password;
	
	@Column(unique=true, nullable=false)
	private String email;
	
	private byte[] userImage;

	@Enumerated(EnumType.STRING)
	private Restriction restriction;

	@OneToMany(mappedBy = "forumUser", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<Comment>();

	@ManyToMany (cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinTable(name = "forum_user_comment", joinColumns = @JoinColumn(name = "forum_user_ident"), inverseJoinColumns = @JoinColumn(name = "comment_ident"))
	private Set<Comment> liked = new HashSet<Comment>();

	public void addComment(Comment comment) {
		comments.add(comment);
		comment.setForumUser(this);
	}

	public void removeComment(Comment comment) {
		comments.remove(comment);
		comment.setForumUser(null);
	}

	public void addLikedComment(Comment likedComment) {
		liked.add(likedComment);
		likedComment.getLikers().add(this);
	}

	public void removeLikedComment(Comment likedComment) {
		liked.remove(likedComment);
		likedComment.getLikers().remove(this);

	}

	public Set<Comment> getLikedComments() {
		return liked;
	}

	public void setLikedComments(Set<Comment> likedComments) {
		this.liked = likedComments;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ForumUser))
			return false;
		return ident != null && ident.equals(((ForumUser) o).getIdent());
	}

	@Override
	public int hashCode() {
		return 10;
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

	public byte[] getUserImage() {
		return userImage;
	}

	public void setUserImage(byte[] photo) {
		this.userImage = photo;
	}

	@Override
	public String toString() {
		return String.valueOf(this.hashCode());
	}

}
