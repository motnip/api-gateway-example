package de.kadmos.usecase.savingservice.service.user;

import de.kadmos.usecase.savingservice.exception.UserNotFoundException;
import de.kadmos.usecase.savingservice.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserServiceInterface {

  boolean userExists(Integer userId) throws UserNotFoundException;

  User findUser(Integer userId) throws UserNotFoundException;
}
