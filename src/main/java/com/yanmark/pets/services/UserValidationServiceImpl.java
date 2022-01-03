package com.yanmark.pets.services;

import org.springframework.stereotype.Service;

@Service
public class UserValidationServiceImpl implements UserValidationService {

  @Override
  public boolean isValidStrings(
      String username, String password, String confirmPassword, String email) {
    if (username == null || password == null || confirmPassword == null || email == null) {
      return false;
    }
    if (username.trim().equals("")
        || password.trim().equals("")
        || confirmPassword.trim().equals("")
        || email.trim().equals("")) {
      return false;
    }
    if (username.length() < 3 || username.length() > 15) {
      return false;
    }
    if (password.length() < 6 || confirmPassword.length() < 6) {
      return false;
    }
    if (email.length() < 8) {
      return false;
    }
    return true;
  }
}
