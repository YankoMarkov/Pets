package com.yanmark.pets.services;

import com.yanmark.pets.domain.entities.Animal;
import com.yanmark.pets.domain.models.bindings.animals.AnimalCreateBindingModel;
import com.yanmark.pets.domain.models.services.AnimalServiceModel;
import com.yanmark.pets.repositories.AnimalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalServiceImpl implements AnimalService {
	
	private static final String ANIMAL_NAME_ALREADY_EXIST = "Animal with this name already exist!";
	private static final String ANIMAL_ID_WAS_NOT_FOUND = "Animal with this id was not found!";
	private static final String ANIMAL_NAME_WAS_NOT_FOUND = "Animal with this name was not found!";
	
	private final AnimalRepository animalRepository;
	private final ModelMapper modelMapper;
	
	@Autowired
	public AnimalServiceImpl(AnimalRepository animalRepository,
	                         ModelMapper modelMapper) {
		this.animalRepository = animalRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public AnimalServiceModel saveAnimal(AnimalServiceModel animalService) {
		Animal checkAnimal = this.animalRepository.findByName(animalService.getName())
				.orElse(null);
		if (checkAnimal != null) {
			throw new IllegalArgumentException(ANIMAL_NAME_ALREADY_EXIST);
		}
		Animal animal = this.modelMapper.map(animalService, Animal.class);
		try {
			animal = this.animalRepository.save(animal);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return this.modelMapper.map(animal, AnimalServiceModel.class);
	}
	
	@Override
	public AnimalServiceModel editAnimal(AnimalCreateBindingModel animalCreate, String id) {
		Animal animal = this.animalRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(ANIMAL_ID_WAS_NOT_FOUND));
		AnimalServiceModel animalServiceModel = this.modelMapper.map(animal, AnimalServiceModel.class);
		animalServiceModel.setName(animalCreate.getName());
		return saveAnimal(animalServiceModel);
	}
	
	@Override
	public void deleteAnimal(String id) {
		try {
			this.animalRepository.deleteById(id);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	@Override
	public AnimalServiceModel getAnimalById(String id) {
		Animal animal = this.animalRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(ANIMAL_ID_WAS_NOT_FOUND));
		return this.modelMapper.map(animal, AnimalServiceModel.class);
	}
	
	@Override
	public AnimalServiceModel getAnimalByName(String name) {
		Animal animal = this.animalRepository.findByName(name)
				.orElseThrow(() -> new IllegalArgumentException(ANIMAL_NAME_WAS_NOT_FOUND));
		return this.modelMapper.map(animal, AnimalServiceModel.class);
		
	}
	
	@Override
	public List<AnimalServiceModel> getAllAnimals() {
		return this.animalRepository.findAllAnimals().stream()
				.map(animal -> this.modelMapper.map(animal, AnimalServiceModel.class))
				.collect(Collectors.toUnmodifiableList());
	}
}
