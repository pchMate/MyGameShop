package mygameshop.Service;

import mygameshop.Models.GameModel;
import mygameshop.Repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public final class GameService {
    @Autowired
    private GameRepository gameRepository;

    public GameModel save(final GameModel model) {
        return gameRepository.save(model);
    }

    public Optional<GameModel> findById(final int id) {
        return gameRepository.findById(id);
    }

    public void deleteById(final int id) {
        gameRepository.deleteById(id);
    }

    public List<GameModel> getAll() {
        return gameRepository.findAll();
    }
}
