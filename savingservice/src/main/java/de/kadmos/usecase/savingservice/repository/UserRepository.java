package de.kadmos.usecase.savingservice.repository;

import de.kadmos.usecase.savingservice.model.User;
import java.util.Optional;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

  Optional<User> findUserById(Integer id);
}
