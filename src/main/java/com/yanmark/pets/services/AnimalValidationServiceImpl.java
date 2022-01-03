package com.yanmark.pets.services;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AnimalValidationServiceImpl implements AnimalValidationService {

  @Override
  public boolean isValidString(String str) {
    if (str == null) {
      return false;
    }
    if (str.trim().equals("")) {
      return false;
    }
    if (str.length() < 2 || str.length() > 15) {
      return false;
    }
    Pattern pattern = Pattern.compile("[A-Za-z]+");
    Matcher matcher = pattern.matcher(str);
    return matcher.matches();
  }
}
