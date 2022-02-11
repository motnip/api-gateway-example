package de.kadmos.usecase.savingservice.repository;

import de.kadmos.usecase.savingservice.model.CheckingAccount;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckingAccountRepository extends CrudRepository<CheckingAccount, Integer> {

  Optional<CheckingAccount> findCheckingAccountByAccountNumber(String accountNumber);
}
