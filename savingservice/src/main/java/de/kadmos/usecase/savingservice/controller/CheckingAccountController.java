package de.kadmos.usecase.savingservice.controller;

import de.kadmos.usecase.savingservice.exception.CheckingAccountNotFoundException;
import de.kadmos.usecase.savingservice.exception.UserNotFoundException;
import de.kadmos.usecase.savingservice.model.Balance;
import de.kadmos.usecase.savingservice.service.account.CheckingAccountServiceInterface;
import de.kadmos.usecase.savingservice.service.user.UserServiceInterface;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/savings/users/{userId}/accounts/{accountId}")
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

  @GetMapping("/balance")
  public Balance getBalance(@PathVariable Integer userId, @PathVariable String accountId)
      throws UserNotFoundException, CheckingAccountNotFoundException {

    //validateUser(userId);
    return accountService.getBalance(accountId, userId);

  }

  @PostMapping("/deposit")
  @ResponseStatus(HttpStatus.CREATED)
  public Balance deposit(
      @PathVariable Integer userId,
      @PathVariable String accountId,
      @RequestParam BigDecimal amount)
      throws UserNotFoundException, CheckingAccountNotFoundException {

    //validateUser(userId);
    return accountService.increaseBalance(amount,accountId, userId);
  }

  @PostMapping("/withdraw")
  @ResponseStatus(HttpStatus.CREATED)
  public Balance withdraw(
      @PathVariable Integer userId,
      @PathVariable String accountId,
      @RequestParam BigDecimal amount)
      throws CheckingAccountNotFoundException, UserNotFoundException {

    //validateUser(userId);
    return accountService.decreaseBalance(amount,accountId, userId);
  }

  private void validateUser(Integer userId) throws UserNotFoundException {
    if (!userService.userExists(userId)) {
      throw new UserNotFoundException(userId);
    }
  }

}
