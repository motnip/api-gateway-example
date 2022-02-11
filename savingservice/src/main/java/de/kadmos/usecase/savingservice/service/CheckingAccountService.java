package de.kadmos.usecase.savingservice.service;

import de.kadmos.usecase.savingservice.exception.CheckingAccountNotFound;
import de.kadmos.usecase.savingservice.model.CheckingAccount;
import de.kadmos.usecase.savingservice.repository.CheckingAccountRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckingAccountService {

  private final CheckingAccountRepository accountRepository;

  @Autowired
  public CheckingAccountService(CheckingAccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  void increaseBalance(String accountNumber, BigDecimal amount) {
    CheckingAccount checkingAccount = getCheckingAccount(accountNumber);

    checkingAccount.setAmount(checkingAccount.getAmount().add(amount));

    accountRepository.save(checkingAccount);

  }

  void descreaseBalance(String accountNumber, BigDecimal amount) {

    CheckingAccount checkingAccount = getCheckingAccount(accountNumber);

    checkingAccount.setAmount(checkingAccount.getAmount().subtract(amount));

    accountRepository.save(checkingAccount);

  }

  private CheckingAccount getCheckingAccount(String accountNumber) {
    return accountRepository
        .findCheckingAccountByAccountNumber(accountNumber)
        .orElseThrow(() -> new CheckingAccountNotFound(
            "Account number" + accountNumber + " does not exixts"));
  }

}
