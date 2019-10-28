package com.yanmark.pets.services;

import com.yanmark.pets.domain.models.services.PetServiceModel;
import org.springframework.data.domain.Page;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

public interface HomeService {
	
	Page<PetServiceModel> takePetsByUser(String animalId,
	                                     ModelAndView modelAndView,
	                                     Principal principal,
	                                     HttpServletRequest request);
	
	Page<PetServiceModel> takeAllPets(String userId,
	                                  HttpServletRequest request);
}
