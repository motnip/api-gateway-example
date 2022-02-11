package de.kadmos.usecase.savingservice.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class CheckingAccount {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String accountNumber;
  private BigDecimal amount;
  private LocalDateTime creationDate;
  private LocalDateTime updateDate;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User userId;
}
