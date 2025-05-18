package mygameshoptest.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import mygameshop.Models.GameModel;
import mygameshop.Repository.GameRepository;
import mygameshop.Service.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
    @Mock
    private GameRepository gameRepositoryMock;

    @InjectMocks
    private GameService gameService;

    @Test
    void getAllBooks() {
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
