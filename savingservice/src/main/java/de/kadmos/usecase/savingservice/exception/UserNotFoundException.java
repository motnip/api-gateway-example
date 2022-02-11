package de.kadmos.usecase.savingservice.exception;

import java.util.Locale;

public class UserNotFoundException extends Exception {

  public UserNotFoundException(Integer userId) {
    super(String.format("User with ID {} not found",userId));
  }

  public UserNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
