package com.yanmark.pets.services;

import com.yanmark.pets.domain.models.services.PetServiceModel;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

public interface HomeService {
	
	List<PetServiceModel> takePets(String animalId,
	                               ModelAndView modelAndView,
	                               Principal principal);
}
