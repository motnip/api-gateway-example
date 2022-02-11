package de.kadmos.usecase.savingservice.exception;

public class CheckingAccountNotFound extends RuntimeException {

  public CheckingAccountNotFound(String message) {
    super(message);
  }

  public CheckingAccountNotFound(String message, Throwable cause) {
    super(message, cause);
  }
}
