package com.yanmark.pets.services;

import org.springframework.stereotype.Service;

@Service
public class IllnessValidationServiceImpl implements IllnessValidationService {

  @Override
  public boolean isValidStrings(String date, String name) {
    if (date == null || name == null) {
      return false;
    }
    if (date.trim().equals("") || name.trim().equals("")) {
      return false;
    }
    if (date.length() < 6 || name.length() < 3) {
      return false;
    }
    return true;
  }

  @Override
  public boolean isValidString(String name) {
    if (name == null) {
      return false;
    }
    if (name.trim().equals("")) {
      return false;
    }
    if (name.length() < 3) {
      return false;
    }
    return true;
  }
}
