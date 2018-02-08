package forum;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import forum.entity.Tag;
import forum.services.TagService;
import forum.services.impl.TagServiceJPA;


@RunWith(MockitoJUnitRunner.class)
public class TagServiceTest {

@Mock
private EntityManager entityManager;

@InjectMocks
private TagService tagService = new TagServiceJPA();

@Test
public  void addTagTest() {
	Tag tag = new Tag();
	tagService.addTag(tag);
	Mockito.verify(entityManager, Mockito.times(1)).persist(tag);
}


}