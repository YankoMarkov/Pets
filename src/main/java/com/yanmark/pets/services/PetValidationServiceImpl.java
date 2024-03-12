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
		if (image == null || image.isEmpty()
				|| animal.isBlank()
				|| name.isBlank()
				|| birthDate.isBlank()
				|| gender.isBlank()
				|| isCastrated.isBlank()) {
			return false;
		}
		return name.length() >= 3 && name.length() <= 30;
	}
}
