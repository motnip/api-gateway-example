package de.kadmos.usecase.savingservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {

  @Id
  private Integer id;
  private String name;
  private String surname;
  private String taxId;
}
