package de.kadmos.usecase.savingservice.controller;

import de.kadmos.usecase.savingservice.exception.CheckingAccountNotFoundException;
import de.kadmos.usecase.savingservice.exception.UserNotFoundException;
import de.kadmos.usecase.savingservice.model.Balance;
import de.kadmos.usecase.savingservice.model.CheckingAccount;
import de.kadmos.usecase.savingservice.service.account.CheckingAccountServiceInterface;
import de.kadmos.usecase.savingservice.service.user.UserServiceInterface;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/a/savings")
public class CheckingAccountController {

  private CheckingAccountServiceInterface accountService;
  private UserServiceInterface userService;

  @Autowired
  public CheckingAccountController(
      CheckingAccountServiceInterface checkingAccountService,
      UserServiceInterface userService) {
    this.accountService = checkingAccountService;
    this.userService = userService;
  }

  @GetMapping("/users/{userId}/accounts/{accountId}/balance")
  public Balance getBalance(@PathVariable Integer userId, @PathVariable String accountId)
      throws UserNotFoundException, CheckingAccountNotFoundException {

    validateUser(userId);
    return accountService.getBalance(accountId);

  }

  @PostMapping("/users/{userId}/accounts/{accountId}/deposit")
  public HttpStatus deposit(
      @PathVariable Integer userId,
      @PathVariable String accountId,
      @RequestParam BigDecimal amount)
      throws UserNotFoundException, CheckingAccountNotFoundException {

    validateUser(userId);

    accountService.increaseBalance(accountId, amount);
    return HttpStatus.CREATED;
  }

  @PostMapping("/{userId}/accounts/{accountId}/withdraw")
  public HttpEntity<CheckingAccount> withdraw(
      @PathVariable String userId,
      @PathVariable String accountId,
      @RequestParam BigDecimal amount) {

    return null;
  }

  private void validateUser(Integer userId) throws UserNotFoundException {
    if (!userService.userExists(userId)) {
      throw new UserNotFoundException(userId);
    }
  }

}
