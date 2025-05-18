package mygameshop.Service;

import mygameshop.Models.RegisteredUserModel;
import mygameshop.Repository.RegisterUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public final class RegisterUserService {

    @Autowired
    private RegisterUserRepository registerUserRepository;

    public RegisteredUserModel save(final RegisteredUserModel model) {
        return registerUserRepository.save(model);
    }

    public Optional<RegisteredUserModel> findById(final int id) {
        return registerUserRepository.findById(id);
    }

    public void deleteById(final int id) {
        registerUserRepository.deleteById(id);
    }

    public Optional<RegisteredUserModel> findByName(
            final String name, String passhash) {
        return registerUserRepository.findByNameAndPasshash(name, passhash);
    }
}
