package forum.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ForumUser {
	
	@Id
	@GeneratedValue
	private Long ident;
	
	private String login;
	private String password;
	private String email;

	@Enumerated(EnumType.STRING)
	private Restriction restriction;
	
	
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
	
	
	
	
	

}
