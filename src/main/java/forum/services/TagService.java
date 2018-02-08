package forum.services;

import java.util.List;

import forum.entity.Tag;

public interface TagService {

	public void addTag(Tag tag);	
	public List<Tag> getAllTags();
	public  Tag getTag(Long ident);
	public void deleteTag(Tag tag);
	public void updateTag(Long ident, String name);
}
