package de.kadmos.usecase.savingservice.model;

import java.awt.print.Book;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String name;
  private String surname;
  private String taxId;
  @OneToMany(mappedBy = "user")
  //TODO make this collection lazy, we don't need it everytime
  private List<CheckingAccount> checkingAccounts;
}
