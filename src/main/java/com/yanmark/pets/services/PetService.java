package com.yanmark.pets.services;

import com.yanmark.pets.domain.models.bindings.pets.PetCreateBindingModel;
import com.yanmark.pets.domain.models.bindings.pets.PetEditBindingModel;
import com.yanmark.pets.domain.models.services.PetServiceModel;
import com.yanmark.pets.domain.models.services.UserServiceModel;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface PetService {
	
	PetServiceModel savePet(PetServiceModel petService,
	                        PetCreateBindingModel petCreate,
	                        Principal principal) throws IOException;
	
	PetServiceModel updatePet(PetServiceModel petService,
	                          PetEditBindingModel petEdit);
	
	PetServiceModel addIllness(PetServiceModel petService);
	
	List<PetServiceModel> getAllPetsByOwner(UserServiceModel userService);
	
	PetServiceModel getPetByName(String name);
	
	PetServiceModel getPetById(String id);
	
	Boolean vaccineDateExpired(String id);
	
	void deletePet(String id);
}
