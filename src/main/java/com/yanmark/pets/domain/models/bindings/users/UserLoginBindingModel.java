package com.yanmark.pets.domain.models.bindings.users;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserLoginBindingModel {

  private String username;
  private String password;

  @NotBlank(message = "Username cannot be null or empty.")
  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @NotBlank(message = "Password cannot be null or empty.")
  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
