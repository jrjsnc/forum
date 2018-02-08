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

// TODO: Auto-generated Javadoc
/**
 * The Class ForumUser.
 */
@Entity(name = "ForumUser")
@Table(name = "forum_user")
public class ForumUser {

	/** The ident. */
	@Id
	@GeneratedValue
	private Long ident;

	/** The login. */
	@Column(unique=true, nullable=false)
	private String login;
	
	/** The password. */
	@Column(nullable=false)
	private String password;
	
	/** The email. */
	@Column(unique=true, nullable=false)
	private String email;
	
	/** The user image. */
	private byte[] userImage;

	/** The restriction. */
	@Enumerated(EnumType.STRING)
	private Restriction restriction;

	/** The comments. */
	@OneToMany(mappedBy = "forumUser", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<Comment>();

	/** The liked. */
	@ManyToMany (cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinTable(name = "forum_user_comment", joinColumns = @JoinColumn(name = "forum_user_ident"), inverseJoinColumns = @JoinColumn(name = "comment_ident"))
	private Set<Comment> liked = new HashSet<Comment>();

	/**
	 * Adds the comment.
	 *
	 * @param comment the comment
	 */
	public void addComment(Comment comment) {
		comments.add(comment);
		comment.setForumUser(this);
	}

	/**
	 * Removes the comment.
	 *
	 * @param comment the comment
	 */
	public void removeComment(Comment comment) {
		comments.remove(comment);
		comment.setForumUser(null);
	}

	/**
	 * Adds the liked comment.
	 *
	 * @param likedComment the liked comment
	 */
	public void addLikedComment(Comment likedComment) {
		liked.add(likedComment);
		likedComment.getLikers().add(this);
	}

	/**
	 * Removes the liked comment.
	 *
	 * @param likedComment the liked comment
	 */
	public void removeLikedComment(Comment likedComment) {
		liked.remove(likedComment);
		likedComment.getLikers().remove(this);

	}

	/**
	 * Gets the liked comments.
	 *
	 * @return the liked comments
	 */
	public Set<Comment> getLikedComments() {
		return liked;
	}

	/**
	 * Sets the liked comments.
	 *
	 * @param likedComments the new liked comments
	 */
	public void setLikedComments(Set<Comment> likedComments) {
		this.liked = likedComments;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ForumUser))
			return false;
		return ident != null && ident.equals(((ForumUser) o).getIdent());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return 10;
	}

	/**
	 * Gets the restriction.
	 *
	 * @return the restriction
	 */
	public Restriction getRestriction() {
		return restriction;
	}

	/**
	 * Sets the restriction.
	 *
	 * @param restriction the new restriction
	 */
	public void setRestriction(Restriction restriction) {
		this.restriction = restriction;
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
	 * Gets the login.
	 *
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
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
	 * Sets the login.
	 *
	 * @param login the new login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the user image.
	 *
	 * @return the user image
	 */
	public byte[] getUserImage() {
		return userImage;
	}

	/**
	 * Sets the user image.
	 *
	 * @param photo the new user image
	 */
	public void setUserImage(byte[] photo) {
		this.userImage = photo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.valueOf(this.hashCode());
	}

}
