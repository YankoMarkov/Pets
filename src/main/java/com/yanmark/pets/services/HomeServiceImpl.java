package com.yanmark.pets.services;

import com.yanmark.pets.domain.models.services.AnimalServiceModel;
import com.yanmark.pets.domain.models.services.PetServiceModel;
import com.yanmark.pets.domain.models.services.UserServiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Service
public class HomeServiceImpl implements HomeService {
	
	private final PetService petService;
	private final AnimalService animalService;
	private final UserService userService;
	
	@Autowired
	public HomeServiceImpl(PetService petService,
	                       AnimalService animalService,
	                       UserService userService) {
		this.petService = petService;
		this.animalService = animalService;
		this.userService = userService;
	}
	
	@Override
	public Page<PetServiceModel> takePetsByUser(String animalId,
	                                            ModelAndView modelAndView,
	                                            Principal principal,
	                                            HttpServletRequest request) {
		UserServiceModel userServiceModel = this.userService.getUserByUsername(principal.getName());
		Page<PetServiceModel> petServiceModels = this.petService.getAllPetsByOwner(userServiceModel, request);
		if (animalId != null && animalId.length() > 0) {
			AnimalServiceModel animalServiceModel = this.animalService.getAnimalById(animalId);
			petServiceModels = this.petService.getAllPetsByOwnerAndAnimal(userServiceModel, animalServiceModel, request);
			modelAndView.addObject("animalName", animalServiceModel.getName());
		}
		return petServiceModels;
	}
	
	@Override
	public Page<PetServiceModel> takeAllPets(String userId,
	                                         HttpServletRequest request) {
		Page<PetServiceModel> petServiceModels = this.petService.getAllPets(request);
		if (userId != null && userId.length() > 0) {
			UserServiceModel userServiceModel = this.userService.getUserById(userId);
			petServiceModels = this.petService.getAllPetsByOwner(userServiceModel, request);
		}
		return petServiceModels;
	}
}
