package de.kadmos.usecase.savingservice.repository;

import de.kadmos.usecase.savingservice.model.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

  Optional<User> findUserById(Integer id);
}
