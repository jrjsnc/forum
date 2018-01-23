package forum.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ForumUser {
	
	@Id
	@GeneratedValue
	private int ident;
	
	private String login;
	private String password;
	private String email;
	//private boolean isAdmin;
	
//	public boolean isAdmin() {
//		return isAdmin;
//	}
//	
//	public void setAdmin(boolean isAdmin) {
//		this.isAdmin = isAdmin;
//	}
	
	public int getIdent() {
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
	public void setIdent(int ident) {
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
