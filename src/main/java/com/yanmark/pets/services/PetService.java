package com.yanmark.pets.services;

import com.yanmark.pets.domain.models.bindings.pets.PetCreateBindingModel;
import com.yanmark.pets.domain.models.bindings.pets.PetEditBindingModel;
import com.yanmark.pets.domain.models.services.AnimalServiceModel;
import com.yanmark.pets.domain.models.services.PetServiceModel;
import com.yanmark.pets.domain.models.services.UserServiceModel;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

public interface PetService {
	
	PetServiceModel savePet(PetServiceModel petService,
	                        PetCreateBindingModel petCreate,
	                        Principal principal) throws IOException;
	
	PetServiceModel updatePet(PetServiceModel petService,
	                          PetEditBindingModel petEdit) throws IOException;
	
	PetServiceModel addIllness(PetServiceModel petService);
	
	Page<PetServiceModel> getAllPetsByOwner(UserServiceModel userService,
	                                        HttpServletRequest request);
	
	Page<PetServiceModel> getAllPetsByOwnerAndAnimal(UserServiceModel userService,
	                                                 AnimalServiceModel animalService,
	                                                 HttpServletRequest request);
	
	PetServiceModel getPetByName(String name);
	
	PetServiceModel getPetById(String id);
	
	Boolean vaccineDateExpired(String id);
	
	void deletePet(String id);
}
