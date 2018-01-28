package forum.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity(name = "Tag")
@Table(name = "tag")
public class Tag {

	@Id
	@GeneratedValue
	private Long ident;

	private String name;

	@ManyToMany(mappedBy = "tags")
	private Set<Topic> topics = new HashSet<>();

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Tag))
			return false;
		return ident != null && ident.equals(((Tag) o).getIdent());
	}

	@Override
	public int hashCode() {
		return 29;
	}		
	
	public Tag(String name) {
		this.name = name;
	}
	
	public Tag() {		
	}
	
	public Set<Topic> getTopics() {
		return topics;
	}
	
	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}

	public Long getIdent() {
		return ident;
	}

	public void setIdent(Long ident) {
		this.ident = ident;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
