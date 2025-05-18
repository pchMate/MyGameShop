package mygameshop.Service;

import mygameshop.Models.TagModel;
import mygameshop.Repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    @Test
    void save() {
        TagModel model1 = new TagModel();
        model1.id = 1;
        model1.tag = "title";
        when(tagRepository.save(model1)).thenReturn(model1);
        TagModel modelSaved2 = tagService.save(model1);
        assertEquals(model1, modelSaved2);
    }

    @Test
    void findById() {
        //GIVEN
        TagModel model2 = new TagModel();
        model2.id = 2;
        model2.tag = "title";
        Optional<TagModel> optional = Optional.of(model2);
        when(tagRepository.findById(model2.id)).thenReturn(optional);
        Optional<TagModel> ret = tagService.findById(model2.id);
        assertEquals(optional, ret);
    }

    @Test
    void deleteById() {
        //GIVEN
        TagModel model1 = new TagModel();
        model1.id = 1;
        model1.tag = "title";
        tagRepository.deleteById(model1.id);
        tagService.deleteById(model1.id);
    }

    @Test
    void getAll() {
        //GIVEN
        TagModel model1 = new TagModel();
        model1.id = 1;
        model1.tag = "title";
        List<TagModel> tagModels = List.of(model1);
        when(tagRepository.findAll()).thenReturn(tagModels);
        //WHEN
        List<TagModel> result = tagService.getAll();
        //THEN
        assertEquals(tagModels, result);
    }
}