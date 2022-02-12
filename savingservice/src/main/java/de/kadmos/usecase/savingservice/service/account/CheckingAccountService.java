package de.kadmos.usecase.savingservice.service.account;

import de.kadmos.usecase.savingservice.exception.CheckingAccountNotFoundException;
import de.kadmos.usecase.savingservice.model.Balance;
import de.kadmos.usecase.savingservice.model.CheckingAccount;
import de.kadmos.usecase.savingservice.repository.CheckingAccountRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckingAccountService implements CheckingAccountServiceInterface {

  private final CheckingAccountRepository accountRepository;

  @Autowired
  public CheckingAccountService(CheckingAccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Balance getBalance(String accountNumber) throws CheckingAccountNotFoundException {

    CheckingAccount checkingAccount = getCheckingAccount(accountNumber);
    return new Balance(checkingAccount.getAmount(), checkingAccount.getUpdateDate());
  }

  @Override
  public Balance increaseBalance(String accountNumber, BigDecimal amount)
      throws CheckingAccountNotFoundException {

    CheckingAccount checkingAccount = getCheckingAccount(accountNumber);
    checkingAccount.setAmount(checkingAccount.getAmount().add(amount));
    accountRepository.save(checkingAccount);

    return new Balance(checkingAccount.getAmount(), LocalDateTime.now());
  }

  @Override
  public void decreaseBalance(String accountNumber, BigDecimal amount)
      throws CheckingAccountNotFoundException {

    CheckingAccount checkingAccount = getCheckingAccount(accountNumber);

    checkingAccount.setAmount(checkingAccount.getAmount().subtract(amount));

    accountRepository.save(checkingAccount);

  }

  private CheckingAccount getCheckingAccount(String accountNumber)
      throws CheckingAccountNotFoundException {
    return accountRepository
        .findCheckingAccountByAccountNumber(accountNumber)
        .orElseThrow(() -> new CheckingAccountNotFoundException(
            "Account number" + accountNumber + " does not exixts"));
  }

}
