/*
 *authors: Denisa Cekanova, Juraj Senic, Miro Sotak, Tomas Siman
 *project name: Movie forum
 *company: T-systems
 * (c)2018 
 */
package forum.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class Tag.
 */
@Entity(name = "Tag")
@Table(name = "tag")
public class Tag {

	/** The ident. */
	@Id
	@GeneratedValue
	private Long ident;

	/** The name. */
	@Column(unique=true, nullable=false) 
	private String name;

	/** The topics. */
	@ManyToMany(mappedBy = "tags")
	private Set<Topic> topics = new HashSet<Topic>();

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Tag))
			return false;
		return ident != null && ident.equals(((Tag) o).getIdent());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return 29;
	}		
	
	/**
	 * Instantiates a new tag.
	 *
	 * @param name the name
	 */
	public Tag(String name) {
		this.name = name;
	}
	
	/**
	 * Instantiates a new tag.
	 */
	public Tag() {		
	}
	
	/**
	 * Gets the topics.
	 *
	 * @return the topics
	 */
	public Set<Topic> getTopics() {
		return topics;
	}
	
	/**
	 * Sets the topics.
	 *
	 * @param topics the new topics
	 */
	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
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
	 * Sets the ident.
	 *
	 * @param ident the new ident
	 */
	public void setIdent(Long ident) {
		this.ident = ident;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {		
		return String.format("Tag(%d %s)", ident, name);		
	}

}
