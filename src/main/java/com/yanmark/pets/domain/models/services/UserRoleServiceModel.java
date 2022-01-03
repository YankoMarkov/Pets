package com.yanmark.pets.domain.models.services;

public class UserRoleServiceModel extends BaseServiceModel {

  private String authority;

  public String getAuthority() {
    return this.authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }
}
