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


/**
 * The Class Tag.
 */
@Entity(name = "Tag")
@Table(name = "tag")
public class Tag {

	/** The ident of tag. Ident is unique and generated automatically. */
	@Id
	@GeneratedValue
	private Long ident;

	/** The name of each tag. Name is unique and can't be null. */
	@Column(unique=true, nullable=false) 
	private String name;

	/** The many to many relation with entity Topic. Sets topics selected by tags. */
	@ManyToMany(mappedBy = "tags")
	private Set<Topic> topics = new HashSet<Topic>();

	/* method equals. Return true when ident of object equals with ident of tag. 
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Tag))
			return false;
		return ident != null && ident.equals(((Tag) o).getIdent());
	}

	/* return the hashcode of tag.
	 */
	@Override
	public int hashCode() {
		return 29;
	}		
	
	/**
	 * Constructor with one parameter.
	 *
	 * @param name of tag.
	 */
	public Tag(String name) {
		this.name = name;
	}
	
	/**
	 * Empty constructor.
	 */
	public Tag() {		
	}
	
	/**
	 * Gets the Set of topics for which tag can be assign.
	 *
	 * @return the Set of topics
	 */
	public Set<Topic> getTopics() {
		return topics;
	}
	
	/**
	 * Sets the topics for which tag can be assign.
	 *
	 * @param topics to set.
	 */
	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}

	/**
	 * Gets the ident of tag.
	 *
	 * @return the ident of tag.
	 */
	public Long getIdent() {
		return ident;
	}

	/**
	 * Sets the ident of tag.
	 *
	 * @param ident of tag to set.
	 */
	public void setIdent(Long ident) {
		this.ident = ident;
	}

	/**
	 * Gets the tag's name.
	 *
	 * @return the name of tag.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the tag's name.
	 *
	 * @param name of tag to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/* method toString return the string format of tag (ident of tag, name of tag).
	 */
	@Override
	public String toString() {		
		return String.format("Tag(%d %s)", ident, name);		
	}

}
