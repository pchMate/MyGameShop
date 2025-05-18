package mygameshop.Repository;

import mygameshop.Models.GameModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameModel, Integer> {
}
