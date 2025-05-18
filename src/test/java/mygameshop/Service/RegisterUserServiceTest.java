package mygameshop.Service;

import mygameshop.Models.RegisteredUserModel;
import mygameshop.Repository.RegisterUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterUserServiceTest {

    @Mock
    private RegisterUserRepository registerUserRepository;

    @InjectMocks
    private RegisterUserService registerUserService;

    @Test
    void save() {
        RegisteredUserModel model2 = new RegisteredUserModel();
        model2.id = 2;
        model2.name = "title";
        when(registerUserRepository.save(model2)).thenReturn(model2);
        RegisteredUserModel modelServiceMock = registerUserService.save(model2);
        assertEquals(model2, modelServiceMock);
    }

    @Test
    void findById() {
        //GIVEN
        RegisteredUserModel model2 = new RegisteredUserModel();
        model2.id = 2;
        model2.name = "title";
        Optional<RegisteredUserModel> optional = Optional.of(model2);
        when(registerUserRepository.findById(model2.id)).thenReturn(optional);
        Optional<RegisteredUserModel> ret = registerUserService.findById(model2.id);
        assertEquals(optional, ret);
    }

    @Test
    void deleteById() {
        //GIVEN
        RegisteredUserModel model1 = new RegisteredUserModel();
        model1.id = 1;
        registerUserRepository.deleteById(model1.id);
        registerUserService.deleteById(model1.id);
    }

    @Test
    void findByName() {
        RegisteredUserModel model2 = new RegisteredUserModel();
        model2.id = 2;
        model2.name = "title";
        model2.passhash = "hash";
        Optional<RegisteredUserModel> optional = Optional.of(model2);
        when(registerUserRepository.findByNameAndPasshash(model2.name, model2.passhash)).thenReturn(optional);
        Optional<RegisteredUserModel> ret = registerUserService.findByName(model2.name, model2.passhash);
        assertEquals(optional, ret);
    }
}