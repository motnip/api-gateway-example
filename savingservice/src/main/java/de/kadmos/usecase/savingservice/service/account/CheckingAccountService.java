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
  public Balance getBalance(String accountNumber, Integer userId) throws CheckingAccountNotFoundException {

    CheckingAccount checkingAccount = getCheckingAccount(accountNumber, userId);
    return new Balance(checkingAccount.getAmount(), checkingAccount.getUpdateDate());
  }

  @Override
  public Balance increaseBalance(BigDecimal amount,String accountNumber, Integer userId)
      throws CheckingAccountNotFoundException {

    CheckingAccount checkingAccount = getCheckingAccount(accountNumber,userId);
    checkingAccount.setAmount(checkingAccount.getAmount().add(amount));
    checkingAccount.setUpdateDate(LocalDateTime.now());
    accountRepository.save(checkingAccount);

    return new Balance(checkingAccount.getAmount(), checkingAccount.getUpdateDate());
  }

  @Override
  public Balance decreaseBalance(BigDecimal amount,String accountNumber, Integer userId)
      throws CheckingAccountNotFoundException {

    CheckingAccount checkingAccount = getCheckingAccount(accountNumber, userId);
    checkingAccount.setAmount(checkingAccount.getAmount().subtract(amount));
    checkingAccount.setUpdateDate(LocalDateTime.now());
    accountRepository.save(checkingAccount);

    return new Balance(checkingAccount.getAmount(), checkingAccount.getUpdateDate());
  }

  private CheckingAccount getCheckingAccount(String accountNumber, Integer userId)
      throws CheckingAccountNotFoundException {
    return accountRepository.findCheckingAccountByAccountNumberAndUserId(accountNumber,userId)
        .orElseThrow(() -> new CheckingAccountNotFoundException(
            "Account number" + accountNumber + " does not exixts"));
  }

}
