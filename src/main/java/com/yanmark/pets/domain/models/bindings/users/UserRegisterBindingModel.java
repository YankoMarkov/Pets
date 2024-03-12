package com.yanmark.pets.domain.models.bindings.users;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegisterBindingModel {

  private String username;
  private String password;
  private String confirmPassword;
  private String email;

  @NotBlank(message = "Username cannot be null or empty.")
  @Size(min = 3, max = 15, message = "Username must be in range [3 - 15] symbols.")
  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @NotBlank(message = "Password cannot be null or empty.")
  @Size(min = 6, message = "Password must be minimum 6 symbols.")
  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @NotBlank(message = "Confirm Password cannot be null or empty.")
  @Size(min = 6, message = "Confirm Password must be minimum 6 symbols.")
  public String getConfirmPassword() {
    return this.confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  @NotBlank(message = "Email cannot be null or empty.")
  @Email(message = "Invalid email.")
  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
