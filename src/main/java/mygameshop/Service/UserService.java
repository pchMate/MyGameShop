package mygameshop.Service;

import mygameshop.Models.UserModel;
import mygameshop.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserModel save(final UserModel model) {
        return userRepository.save(model);
    }

    public UserModel edit(final UserModel model) {
        return userRepository.save(model);
    }

    public Optional<UserModel> findById(final int id) {
        return userRepository.findById(id);
    }

    public void deleteById(final int id) {
        userRepository.deleteById(id);
    }
}
