package com.plms.mongoSpringBoot.dto;

import lombok.Data;

@Data
public class AddressDto {

  private String city;

  private String country;

  private String municipality;
}
