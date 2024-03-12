package com.yanmark.pets.domain.models.bindings.users;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserEditBindingModel {

  private String username;
  private String password;
  private String newPassword;
  private String confirmNewPassword;
  private String email;

  @NotNull(message = "Username cannot be null.")
  @Size(min = 3, max = 15, message = "Username must be in range [3 - 15] symbols.")
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

  @NotBlank(message = "New password cannot be null or empty.")
  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  @NotBlank(message = "Confirm new password cannot be null or empty.")
  public String getConfirmNewPassword() {
    return confirmNewPassword;
  }

  public void setConfirmNewPassword(String confirmNewPassword) {
    this.confirmNewPassword = confirmNewPassword;
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
