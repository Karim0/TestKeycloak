package com.example.testkeycloak.entity;


import lombok.Data;

@Data
public class CustomerEntity {
  private long id;
  private String name;
  private String serviceRendered;
  private String address;
}
