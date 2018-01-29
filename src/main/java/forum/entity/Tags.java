package forum.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Tags {
	
	@Id
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	

}
