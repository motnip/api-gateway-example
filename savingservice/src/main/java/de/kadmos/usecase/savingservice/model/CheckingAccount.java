package de.kadmos.usecase.savingservice.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CheckingAccount {

  @Id
  private Integer id;
  private String accountNumber;
  private BigDecimal amount;
  private LocalDateTime creationDate;
  private LocalDateTime updateDate;
  private User userId;
}
