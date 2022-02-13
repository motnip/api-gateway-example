package de.kadmos.usecase.savingservice.exception;

public class CheckingAccountNotFoundException extends Exception {

  public CheckingAccountNotFoundException(String accountNumber, Integer userId) {
    super(String.format("Account [NUMBER: %s of USER ID: %d] not found",accountNumber, userId));
  }

  public CheckingAccountNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
