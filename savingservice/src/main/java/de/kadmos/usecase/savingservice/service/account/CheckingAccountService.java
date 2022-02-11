package de.kadmos.usecase.savingservice.service.account;

import de.kadmos.usecase.savingservice.exception.CheckingAccountNotFound;
import de.kadmos.usecase.savingservice.model.CheckingAccount;
import de.kadmos.usecase.savingservice.repository.CheckingAccountRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckingAccountService implements CheckingAccountServiceInterface{

  private final CheckingAccountRepository accountRepository;

  @Autowired
  public CheckingAccountService(CheckingAccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public void increaseBalance(String accountNumber, BigDecimal amount) {
    CheckingAccount checkingAccount = getCheckingAccount(accountNumber);

    checkingAccount.setAmount(checkingAccount.getAmount().add(amount));

    accountRepository.save(checkingAccount);

  }

  @Override
  public void descreaseBalance(String accountNumber, BigDecimal amount) {

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
