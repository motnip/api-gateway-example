package de.kadmos.usecase.savingservice.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.kadmos.usecase.savingservice.exception.CheckingAccountNotFoundException;
import de.kadmos.usecase.savingservice.model.CheckingAccount;
import de.kadmos.usecase.savingservice.repository.CheckingAccountRepository;
import de.kadmos.usecase.savingservice.service.account.CheckingAccountService;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CheckingAccountServiceTest {

  @Mock
  private CheckingAccountRepository repository;

  @InjectMocks
  private CheckingAccountService sut;

  private CheckingAccount checkingAccount;
  private String accountNumber;

  @Captor
  ArgumentCaptor<CheckingAccount> checkingAccountArgumentCaptor;
  private Integer userId;

  @BeforeEach
  void setUp() {
    checkingAccount = new CheckingAccount();
    accountNumber = "DE1234H";
    checkingAccount.setAccountNumber(accountNumber);
    checkingAccount.setAccountNumber(accountNumber);
    userId = 1234;
  }

  @Test
  public void TestIncreaseBalance() throws CheckingAccountNotFoundException {

    BigDecimal initialAmount = BigDecimal.valueOf(100.50);
    BigDecimal newAmount = BigDecimal.valueOf(200.25);

    String accountNumber = "DE1234H";
    CheckingAccount checkingAccount = new CheckingAccount();
    checkingAccount.setAmount(initialAmount);

    when(repository.findCheckingAccountByAccountNumberAndUserId(eq(accountNumber),eq(userId)))
            .thenReturn( Optional.of(checkingAccount));

    sut.increaseBalance(newAmount,accountNumber, userId);

    verify(repository, times(1)).save(checkingAccountArgumentCaptor.capture());

    BigDecimal actualAmount = checkingAccountArgumentCaptor.getValue().getAmount();
    BigDecimal expectedAmount = initialAmount.add(newAmount);

    assertThat(actualAmount.compareTo(expectedAmount), equalTo(0));
  }

  @Test
  public void TestDecreaseBalance() throws CheckingAccountNotFoundException {

    BigDecimal initialAmount = BigDecimal.valueOf(200.50);
    BigDecimal withdrawAmount = BigDecimal.valueOf(100.25);

    String accountNumber = "DE1234H";
    CheckingAccount checkingAccount = new CheckingAccount();
    checkingAccount.setAmount(initialAmount);

    when(repository.findCheckingAccountByAccountNumberAndUserId(eq(accountNumber),eq(userId))).thenReturn(
        Optional.of(checkingAccount));

    sut.decreaseBalance(withdrawAmount, accountNumber, userId);

    verify(repository, times(1)).save(checkingAccountArgumentCaptor.capture());

    BigDecimal actualAmount = checkingAccountArgumentCaptor.getValue().getAmount();
    BigDecimal expectedAmount = initialAmount.subtract(withdrawAmount);

    assertThat(actualAmount.compareTo(expectedAmount), equalTo(0));
  }

  //@Test
  public void whenAccountDoesNotExistThrowException() throws CheckingAccountNotFoundException {

    assertThrows(CheckingAccountNotFoundException.class,
            () -> repository.findCheckingAccountByAccountNumberAndUserId(eq(accountNumber),eq(userId))
                    .orElseThrow(()->new CheckingAccountNotFoundException(anyString()))
    );

    checkingAccount.setAmount(BigDecimal.valueOf(300));

    when(repository.findCheckingAccountByAccountNumberAndUserId(eq(accountNumber),eq(userId))).thenThrow(CheckingAccountNotFoundException.class);

    sut.decreaseBalance(BigDecimal.valueOf(200), accountNumber, userId);

    verify(repository, times(0)).save(any());
  }
}
