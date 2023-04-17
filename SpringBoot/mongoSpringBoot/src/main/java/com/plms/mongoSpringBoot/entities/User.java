package com.plms.mongoSpringBoot.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @Id
  private String id;

  private String name;

  private String lastName;

  private String email;

  private String password;

  private Integer age;

  private Address address;
}
