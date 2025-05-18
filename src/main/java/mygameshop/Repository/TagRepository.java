package mygameshop.Repository;

import mygameshop.Models.TagModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagModel, Integer> {
}
