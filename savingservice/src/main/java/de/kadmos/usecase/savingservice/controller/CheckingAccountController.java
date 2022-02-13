package de.kadmos.usecase.savingservice.controller;

import de.kadmos.usecase.savingservice.exception.CheckingAccountNotFoundException;
import de.kadmos.usecase.savingservice.model.Balance;
import de.kadmos.usecase.savingservice.service.account.CheckingAccountServiceInterface;
import de.kadmos.usecase.savingservice.service.user.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "/savings/users/{userId}/accounts/{accountId}")
public class CheckingAccountController {

  private CheckingAccountServiceInterface accountService;

  @Autowired
  public CheckingAccountController(
      CheckingAccountServiceInterface checkingAccountService) {
    this.accountService = checkingAccountService;
  }

  @GetMapping("/balance")
  public Balance getBalance(@PathVariable Integer userId, @PathVariable String accountId)
      throws CheckingAccountNotFoundException {

    return accountService.getBalance(accountId, userId);
  }

  @PostMapping("/deposit")
  @ResponseStatus(HttpStatus.CREATED)
  public Balance deposit(
      @PathVariable Integer userId, @PathVariable String accountId, @RequestParam BigDecimal amount)
      throws CheckingAccountNotFoundException {

    return accountService.increaseBalance(amount, accountId, userId);
  }

  @PostMapping("/withdraw")
  @ResponseStatus(HttpStatus.CREATED)
  public Balance withdraw(
      @PathVariable Integer userId, @PathVariable String accountId, @RequestParam BigDecimal amount)
      throws CheckingAccountNotFoundException {

    return accountService.decreaseBalance(amount, accountId, userId);
  }
}
