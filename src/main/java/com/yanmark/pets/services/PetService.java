package com.yanmark.pets.services;

import com.yanmark.pets.domain.models.services.PetServiceModel;
import com.yanmark.pets.domain.models.services.UserServiceModel;

import java.util.List;

public interface PetService {
	
	PetServiceModel savePet(PetServiceModel petService);
	
	List<PetServiceModel> getAllPetsByOwner(UserServiceModel userService);
	
	PetServiceModel getPetByName(String name);
}
