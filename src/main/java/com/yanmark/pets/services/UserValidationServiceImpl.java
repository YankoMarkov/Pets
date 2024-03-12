package com.yanmark.pets.services;

import org.springframework.stereotype.Service;

@Service
public class UserValidationServiceImpl implements UserValidationService {
	
	@Override
	public boolean isValidStrings(
			String username, String password, String confirmPassword, String email) {
		if (username.isBlank()
				|| password.isBlank()
				|| confirmPassword.isBlank()
				|| email.isBlank()) {
			return false;
		}
		if (username.length() < 3 || username.length() > 15) {
			return false;
		}
		if (password.length() < 6 || confirmPassword.length() < 6) {
			return false;
		}
		return email.length() >= 8;
	}
}
