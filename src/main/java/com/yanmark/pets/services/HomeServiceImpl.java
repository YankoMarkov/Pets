package com.yanmark.pets.services;

import com.yanmark.pets.domain.models.services.AnimalServiceModel;
import com.yanmark.pets.domain.models.services.PetServiceModel;
import com.yanmark.pets.domain.models.services.UserServiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

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
	public List<PetServiceModel> takePets(String animalId,
	                                      ModelAndView modelAndView,
	                                      Principal principal) {
		UserServiceModel userServiceModel = this.userService.getUserByUsername(principal.getName());
		List<PetServiceModel> petServiceModels = this.petService.getAllPetsByOwner(userServiceModel);
		if (animalId != null && animalId.length() > 0) {
			AnimalServiceModel animalServiceModel = this.animalService.getAnimalById(animalId);
			if (animalServiceModel != null) {
				petServiceModels = petServiceModels.stream()
						.filter(pet -> pet.getAnimal().getId().equals(animalId))
						.collect(Collectors.toList());
				modelAndView.addObject("animalName", animalServiceModel.getName());
			}
		}
		return petServiceModels;
	}
}
