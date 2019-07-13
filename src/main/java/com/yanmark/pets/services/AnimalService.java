package com.yanmark.pets.services;

import com.yanmark.pets.domain.models.services.AnimalServiceModel;

import java.util.List;

public interface AnimalService {
	
	AnimalServiceModel saveAnimal(AnimalServiceModel animalService);
	
	void deleteAnimal(String id);
	
	AnimalServiceModel getAnimalById(String id);
	
	AnimalServiceModel getAnimalByName(String name);
	
	List<AnimalServiceModel> getAllAnimals();
}
