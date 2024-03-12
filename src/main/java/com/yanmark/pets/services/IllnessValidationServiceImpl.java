package com.yanmark.pets.services;

import org.springframework.stereotype.Service;

@Service
public class IllnessValidationServiceImpl implements IllnessValidationService {

  @Override
  public boolean isValidStrings(String date, String name) {
    if (date == null || name == null) {
      return false;
    }
    if (date.isBlank() || name.isBlank()) {
      return false;
    }
	  return date.length() >= 6 && name.length() >= 3;
  }

  @Override
  public boolean isValidString(String name) {
    if (name == null) {
      return false;
    }
    if (name.isBlank()) {
      return false;
    }
	  return name.length() >= 3;
  }
}
