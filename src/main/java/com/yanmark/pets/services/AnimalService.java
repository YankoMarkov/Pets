package com.yanmark.pets.services;

import com.yanmark.pets.domain.models.bindings.animals.AnimalCreateBindingModel;
import com.yanmark.pets.domain.models.services.AnimalServiceModel;

import java.util.List;

public interface AnimalService {

  AnimalServiceModel saveAnimal(AnimalCreateBindingModel animalCreate);

  AnimalServiceModel editAnimal(AnimalCreateBindingModel animalCreate, String id);

  void deleteAnimal(String id);

  AnimalServiceModel getAnimalById(String id);

  AnimalServiceModel getAnimalByName(String name);

  List<AnimalServiceModel> getAllAnimals();
}
