package mygameshop.Service;

import mygameshop.Models.GameModel;
import mygameshop.Models.TagModel;
import mygameshop.Repository.GameRepository;
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
public class GameServiceTest {

    @Mock
    private GameRepository gameRepositoryMock;

    @InjectMocks
    private GameService gameService;

    @Test
    void save() {
        GameModel model2 = new GameModel();
        model2.id = 2;
        model2.title = "title";
        when(gameRepositoryMock.save(model2)).thenReturn(model2);
        GameModel modelServiceMock = gameService.save(model2);
        assertEquals(model2, modelServiceMock);
    }


    @Test
    void findById() {
        GameModel model2 = new GameModel();
        model2.id = 2;
        model2.title = "title";
        Optional<GameModel> optional = Optional.of(model2);
        when(gameRepositoryMock.findById(model2.id)).thenReturn(optional);
        Optional<GameModel> ret = gameService.findById(model2.id);
        assertEquals(optional, ret);
    }

    @Test
    void deleteById() {
        TagModel model1 = new TagModel();
        model1.id = 1;
        model1.tag = "title";
        gameRepositoryMock.deleteById(model1.id);
        gameService.deleteById(model1.id);
    }

    @Test
    void getAll() {
        //GIVEN
        GameModel model1 = new GameModel();
        model1.id = 1;
        model1.title = "title";
        List<GameModel> expectedBooks = List.of(model1);
        when(gameRepositoryMock.findAll()).thenReturn(expectedBooks);
        //WHEN
        List<GameModel> result = gameService.getAll();
        //THEN
        assertEquals(expectedBooks, result);
    }
}