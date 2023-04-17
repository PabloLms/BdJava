package com.plms.mongoSpringBoot.dto;

import lombok.Data;

@Data
public class UserDto {

  private String name;

  private String lastName;

  private String email;

  private String password;

  private Integer age;

  private AddressDto address;
}
