package com.yanmark.pets.services;

public interface UserValidationService {

  boolean isValidStrings(String username, String password, String confirmPassword, String email);
}
