package mygameshop.Service;

import mygameshop.Models.TagModel;
import mygameshop.Repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public final class TagService {
    @Autowired
    private TagRepository tagRepository;

    public TagModel save(final TagModel model) {
        return tagRepository.save(model);
    }

    public Optional<TagModel> findById(final int id) {
        return tagRepository.findById(id);
    }

    public void deleteById(final int id) {
        tagRepository.deleteById(id);
    }

    public List<TagModel> getAll() {
        return tagRepository.findAll();
    }
}
