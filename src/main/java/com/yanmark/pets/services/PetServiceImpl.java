package com.yanmark.pets.services;

import com.yanmark.pets.domain.entities.Pet;
import com.yanmark.pets.domain.entities.User;
import com.yanmark.pets.domain.models.services.PetServiceModel;
import com.yanmark.pets.domain.models.services.UserServiceModel;
import com.yanmark.pets.repositories.PetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {
	
	private final ModelMapper modelMapper;
	private final PetRepository petRepository;
	
	@Autowired
	public PetServiceImpl(ModelMapper modelMapper, PetRepository petRepository) {
		this.modelMapper = modelMapper;
		this.petRepository = petRepository;
	}
	
	@Override
	public PetServiceModel savePet(PetServiceModel petService) {
		Pet pet = this.modelMapper.map(petService, Pet.class);
		try {
			pet = this.petRepository.save(pet);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return this.modelMapper.map(pet, PetServiceModel.class);
	}
	
	@Override
	public List<PetServiceModel> getAllPetsByOwner(UserServiceModel userService) {
		User owner = this.modelMapper.map(userService, User.class);
		return this.petRepository.getAllByOwner(owner).stream()
				.map(pet -> this.modelMapper.map(pet, PetServiceModel.class))
				.collect(Collectors.toUnmodifiableList());
	}
	
	@Override
	public PetServiceModel getPetByName(String name) {
		Pet pet = this.petRepository.getByName(name)
				.orElseThrow(() -> new IllegalArgumentException("Pet was not found!"));
		return this.modelMapper.map(pet, PetServiceModel.class);
	}
}
