package de.kadmos.usecase.savingservice.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

  @BeforeEach
  void setUp() {
    checkingAccount = new CheckingAccount();
    accountNumber = "DE1234H";
    checkingAccount.setAccountNumber(accountNumber);
  }

  @Test
  public void TestIncreaseBalance() throws CheckingAccountNotFoundException {

    BigDecimal initialAmount = BigDecimal.valueOf(100.50);
    BigDecimal newAmount = BigDecimal.valueOf(200.25);

    String accountNumber = "DE1234H";
    CheckingAccount checkingAccount = new CheckingAccount();
    checkingAccount.setAmount(initialAmount);

    when(repository.findCheckingAccountByAccountNumber(eq(accountNumber))).thenReturn(
        Optional.of(checkingAccount));

    sut.increaseBalance(accountNumber, newAmount);

    verify(repository, times(1)).save(checkingAccountArgumentCaptor.capture());

    BigDecimal actualAmount = checkingAccountArgumentCaptor.getValue().getAmount();
    BigDecimal expectedAmount = initialAmount.add(newAmount);

    assertThat(actualAmount.compareTo(expectedAmount), equalTo(0));
  }

  @Test
  public void TestDecreaseBalance() throws CheckingAccountNotFoundException {

    Exception exception = assertThrows(CheckingAccountNotFoundException.class, () -> repository.findCheckingAccountByAccountNumber(eq(accountNumber)));

   checkingAccount.setAmount(BigDecimal.valueOf(300));

    when(repository.findCheckingAccountByAccountNumber(eq(accountNumber))).thenThrow(CheckingAccountNotFoundException.class);

    sut.decreaseBalance(accountNumber, BigDecimal.valueOf(200));

    verify(repository, times(0)).save(any());

  }


  @Test
  public void whenAccountDoesNotExistThrowException() throws CheckingAccountNotFoundException {

    BigDecimal initialAmount = BigDecimal.valueOf(100.50);
    BigDecimal newAmount = BigDecimal.valueOf(200.25);

    String accountNumber = "DE1234H";
    CheckingAccount checkingAccount = new CheckingAccount();
    checkingAccount.setAmount(initialAmount);

    when(repository.findCheckingAccountByAccountNumber(eq(accountNumber))).thenReturn(
        Optional.of(checkingAccount));

    sut.increaseBalance(accountNumber, newAmount);

    verify(repository, times(1)).save(checkingAccountArgumentCaptor.capture());

    BigDecimal actualAmount = checkingAccountArgumentCaptor.getValue().getAmount();
    BigDecimal expectedAmount = initialAmount.add(newAmount);

    assertThat(actualAmount.compareTo(expectedAmount), equalTo(0));
  }
}
