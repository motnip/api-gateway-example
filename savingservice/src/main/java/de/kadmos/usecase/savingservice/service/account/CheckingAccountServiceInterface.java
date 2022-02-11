package de.kadmos.usecase.savingservice.service.account;

import de.kadmos.usecase.savingservice.exception.CheckingAccountNotFoundException;
import de.kadmos.usecase.savingservice.model.Balance;
import java.math.BigDecimal;

public interface CheckingAccountServiceInterface {

  Balance getBalance(String accountNumber) throws CheckingAccountNotFoundException;

  void increaseBalance(String accountNumber, BigDecimal amount) throws CheckingAccountNotFoundException;

  void decreaseBalance(String accountNumber, BigDecimal amount) throws CheckingAccountNotFoundException;
}
