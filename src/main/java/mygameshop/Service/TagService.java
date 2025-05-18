package mygameshop.Service;

import mygameshop.Models.TagModel;
import mygameshop.Repository.GameRepository;
import mygameshop.Repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public TagModel save(final TagModel model) {
        return tagRepository.save(model);
    }

    public TagModel edit(final TagModel model) {
        return tagRepository.save(model);
    }

    public Optional<TagModel> findById(final int id) {
        return tagRepository.findById(id);
    }

    public void deleteById(final int id) {
        tagRepository.deleteById(id);
    }

    public List<TagModel> getAll() {
        try {
            return tagRepository.findAll();
        }
        catch (Exception ignored)
        {
            return new ArrayList<>();
        }
    }
}
