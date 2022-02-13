package de.kadmos.usecase.savingservice.service;

import de.kadmos.usecase.savingservice.exception.CheckingAccountNotFoundException;
import de.kadmos.usecase.savingservice.model.CheckingAccount;
import de.kadmos.usecase.savingservice.repository.CheckingAccountRepository;
import de.kadmos.usecase.savingservice.service.account.CheckingAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckingAccountServiceTest {

  @Captor ArgumentCaptor<CheckingAccount> checkingAccountArgumentCaptor;
  @Mock private CheckingAccountRepository repository;
  @InjectMocks private CheckingAccountService sut;
  private CheckingAccount checkingAccount;
  private String accountNumber;
  private Integer userId;

  @BeforeEach
  void setUp() {
    checkingAccount = new CheckingAccount();
    accountNumber = "DE1234H";
    checkingAccount.setAccountNumber(accountNumber);
    userId = 1234;
  }

  @Test
  public void TestIncreaseBalance() throws CheckingAccountNotFoundException {

    BigDecimal initialAmount = BigDecimal.valueOf(100.50);
    BigDecimal newAmount = BigDecimal.valueOf(200.25);

    checkingAccount.setAmount(initialAmount);

    when(repository.findCheckingAccountByAccountNumberAndUserId(eq(accountNumber), eq(userId)))
        .thenReturn(Optional.of(checkingAccount));

    sut.increaseBalance(newAmount, accountNumber, userId);

    verify(repository, times(1)).save(checkingAccountArgumentCaptor.capture());

    BigDecimal actualAmount = checkingAccountArgumentCaptor.getValue().getAmount();
    BigDecimal expectedAmount = initialAmount.add(newAmount);

    assertThat(actualAmount.compareTo(expectedAmount), equalTo(0));
  }

  @Test
  public void TestDecreaseBalance() throws CheckingAccountNotFoundException {

    BigDecimal initialAmount = BigDecimal.valueOf(200.50);
    BigDecimal withdrawAmount = BigDecimal.valueOf(100.25);

    checkingAccount.setAmount(initialAmount);

    when(repository.findCheckingAccountByAccountNumberAndUserId(eq(accountNumber), eq(userId)))
        .thenReturn(Optional.of(checkingAccount));

    sut.decreaseBalance(withdrawAmount, accountNumber, userId);

    verify(repository, times(1)).save(checkingAccountArgumentCaptor.capture());

    BigDecimal actualAmount = checkingAccountArgumentCaptor.getValue().getAmount();
    BigDecimal expectedAmount = initialAmount.subtract(withdrawAmount);

    assertThat(actualAmount.compareTo(expectedAmount), equalTo(0));
  }

  @Test()
  public void whenAccountDoesNotExistThrowException(){

    Exception exception = assertThrows(
        CheckingAccountNotFoundException.class,
        () -> {
          when(repository.findCheckingAccountByAccountNumberAndUserId(eq(accountNumber), eq(userId)))
              .thenReturn(Optional.empty());
          sut.decreaseBalance(BigDecimal.valueOf(200), accountNumber, userId);
        });

    verify(repository, times(0)).save(any());
    assertThat(exception.getMessage(), is(equalTo("Account [NUMBER: "+accountNumber+" of USER ID: "+userId+"] not found")));
  }
}
