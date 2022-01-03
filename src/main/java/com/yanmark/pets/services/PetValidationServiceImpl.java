package com.yanmark.pets.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PetValidationServiceImpl implements PetValidationService {

  @Override
  public boolean isValidStrings(
      MultipartFile image,
      String animal,
      String name,
      String birthDate,
      String gender,
      String isCastrated) {
    if (image == null
        || animal == null
        || name == null
        || birthDate == null
        || gender == null
        || isCastrated == null) {
      return false;
    }
    if (image.isEmpty()
        || animal.trim().equals("")
        || name.trim().equals("")
        || birthDate.trim().equals("")
        || gender.trim().equals("")
        || isCastrated.trim().equals("")) {
      return false;
    }
    if (name.length() < 3 || name.length() > 30) {
      return false;
    }
    return true;
  }
}
