package de.kadmos.usecase.savingservice.service.account;

import java.math.BigDecimal;

public interface CheckingAccountServiceInterface {

  void increaseBalance(String accountNumber, BigDecimal amount);

  void descreaseBalance(String accountNumber, BigDecimal amount);
}
