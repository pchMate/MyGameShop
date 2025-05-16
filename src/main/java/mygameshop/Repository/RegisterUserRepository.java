package mygameshop.Repository;

import mygameshop.Models.RegisteredUserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisterUserRepository extends JpaRepository<RegisteredUserModel, Integer> {
    Optional<RegisteredUserModel> findByNameAndPasshash(String name, String passhash);
}
