package com.yanmark.pets.services;

import org.springframework.web.multipart.MultipartFile;

public interface PetValidationService {

  boolean isValidStrings(
      MultipartFile image,
      String animal,
      String name,
      String birthDate,
      String gender,
      String isCastrated);
}
