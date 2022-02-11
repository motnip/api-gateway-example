package de.kadmos.usecase.savingservice.exception;

public class CheckingAccountNotFoundException extends Exception {

  public CheckingAccountNotFoundException(String accountNumber) {
    super(String.format("Account NUMBER {} not found",accountNumber));
  }

  public CheckingAccountNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
