package mygameshop.Service;

import mygameshop.Models.UserModel;
import mygameshop.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void save() {
        UserModel model1 = new UserModel();
        model1.id = 1;
        model1.gamesOwned = new ArrayList<>();
        when(userRepository.save(model1)).thenReturn(model1);
        UserModel modelSaved2 = userService.save(model1);
        assertEquals(model1, modelSaved2);
    }

    @Test
    void findById() {
        UserModel model2 = new UserModel();
        model2.id = 2;
        model2.name = "title";
        model2.gamesOwned = new ArrayList<>();
        Optional<UserModel> optional = Optional.of(model2);
        when(userRepository.findById(model2.id)).thenReturn(optional);
        Optional<UserModel> ret = userService.findById(model2.id);
        assertEquals(optional, ret);
    }

    @Test
    void deleteById() {
        UserModel model1 = new UserModel();
        model1.id = 1;
        model1.gamesOwned = new ArrayList<>();
        userRepository.deleteById(model1.id);
        userService.deleteById(model1.id);
    }
}