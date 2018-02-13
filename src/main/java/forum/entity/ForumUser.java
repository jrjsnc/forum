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

/**
 * The Class ForumUser.
 */
@Entity(name = "ForumUser")
@Table(name = "forum_user")
public class ForumUser {

	/** The identof each user. Ident is generated automatically. */
	@Id
	@GeneratedValue
	private Long ident;

	/** The users login. Login is unique and can't be null. */
	@Column(unique = true, nullable = false)
	private String login;

	/** The users password. Password can't be null. */
	@Column(nullable = false)
	private String password;

	/** The users email. Email must be unique and can't be null. */
	@Column(unique = true, nullable = false)
	private String email;

	/** The users image. */
	private byte[] userImage;

	/** The restriction of user. User can be ADMIN, BASIC, BANNED. */
	@Enumerated(EnumType.STRING)
	private Restriction restriction;

	/** The one to many relation with entity comment. */
	@OneToMany(mappedBy = "forumUser", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<Comment>();

	/** The many to many relation with comment. */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "forum_user_comment", joinColumns = @JoinColumn(name = "forum_user_ident"), inverseJoinColumns = @JoinColumn(name = "comment_ident"))
	private Set<Comment> liked = new HashSet<Comment>();

	/**
	 * Adds the comment.
	 *
	 * @param comment
	 *            to add.
	 */
	public void addComment(Comment comment) {
		comments.add(comment);
		comment.setForumUser(this);
	}

	/**
	 * Removes the comment.
	 *
	 * @param comment
	 *            to remove.
	 */
	public void removeComment(Comment comment) {
		comments.remove(comment);
		comment.setForumUser(null);
	}

	/**
	 * Adds the likes to comment.
	 *
	 * @param likedComment.
	 */
	public void addLikedComment(Comment likedComment) {
		liked.add(likedComment);
		likedComment.getLikers().add(this);
	}

	/**
	 * Removes the likes of comment.
	 *
	 * @param likedComment.
	 */
	public void removeLikedComment(Comment likedComment) {
		liked.remove(likedComment);
		likedComment.getLikers().remove(this);

	}

	/**
	 * Gets the set of liked comments.
	 *
	 * @return the liked comments.
	 */
	public Set<Comment> getLikedComments() {
		return liked;
	}

	/**
	 * Sets the liked comments.
	 *
	 * @param likedComments
	 *            to set.
	 */
	public void setLikedComments(Set<Comment> likedComments) {
		this.liked = likedComments;
	}

	/*
	 * method equals. Return true when ident of object equals with ident of user.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ForumUser))
			return false;
		return ident != null && ident.equals(((ForumUser) o).getIdent());
	}

	/*
	 * return the hashcode of user.
	 */
	@Override
	public int hashCode() {
		return 10;
	}

	/**
	 * Gets the restriction to user. User can be ADMIN, BASIC, BANNED.
	 *
	 * @return the restriction of user
	 */
	public Restriction getRestriction() {
		return restriction;
	}

	/**
	 * Sets the restriction. User can be ADMIN, BASIC, BANNED.
	 *
	 * @param restriction
	 *            to set.
	 */
	public void setRestriction(Restriction restriction) {
		this.restriction = restriction;
	}

	/**
	 * Gets the ident of each user.
	 *
	 * @return the ident
	 */
	public Long getIdent() {
		return ident;
	}

	/**
	 * Gets the users login.
	 *
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Gets the users password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the users email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the ident of user.
	 *
	 * @param ident
	 *            to set.
	 */
	public void setIdent(Long ident) {
		this.ident = ident;
	}

	/**
	 * Sets the users login.
	 *
	 * @param login
	 *            to set.
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Sets the users password.
	 *
	 * @param password
	 *            to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Sets the users email.
	 *
	 * @param email
	 *            to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the users image.
	 *
	 * @return the user image.
	 */
	public byte[] getUserImage() {
		return userImage;
	}

	/**
	 * Sets the user image.
	 *
	 * @param photo
	 *            to be set as users image
	 */
	public void setUserImage(byte[] photo) {
		this.userImage = photo;
	}

	/*
	 * method toString return value of hashcode.
	 */
	@Override
	public String toString() {
		return String.valueOf(this.hashCode());
	}

}
