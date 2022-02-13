package de.kadmos.usecase.savingservice.service.account;

import de.kadmos.usecase.savingservice.exception.CheckingAccountNotFoundException;
import de.kadmos.usecase.savingservice.model.Balance;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface CheckingAccountServiceInterface {

  Balance getBalance(String accountNumber, Integer userId) throws CheckingAccountNotFoundException;

  Balance increaseBalance(BigDecimal amount, String accountNumber, Integer userId)
      throws CheckingAccountNotFoundException;

  Balance decreaseBalance(BigDecimal amount, String accountNumber, Integer userId)
      throws CheckingAccountNotFoundException;
}
