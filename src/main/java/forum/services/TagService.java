package forum.services;

import java.util.List;

import forum.entity.Tag;

public interface TagService {

	public void addTag(Tag tag);	
	public List<Tag> getAllTags();
	public void deleteTag(Tag tag);
}
