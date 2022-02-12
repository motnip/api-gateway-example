package de.kadmos.usecase.savingservice.exception;

public class UserNotFoundException extends Exception {

  public UserNotFoundException(Integer userId) {
    super(String.format("User with ID %s not found",userId));
  }

  public UserNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
