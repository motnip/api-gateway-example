package de.kadmos.usecase.savingservice.service.user;

import de.kadmos.usecase.savingservice.exception.UserNotFoundException;
import de.kadmos.usecase.savingservice.model.User;
import de.kadmos.usecase.savingservice.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceInterface {

  private final UserRepository repository;

  @Autowired
  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public boolean userExists(Integer userId) {

    Optional<User> user = repository.findUserById(userId);

    return user.isPresent();
  }

  @Override
  public User findUser(Integer userId) throws UserNotFoundException {

    User user = repository.findUserById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));
    return user;
  }

}
