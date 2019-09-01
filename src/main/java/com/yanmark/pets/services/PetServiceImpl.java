package com.yanmark.pets.services;

import com.yanmark.pets.domain.entities.Pet;
import com.yanmark.pets.domain.entities.User;
import com.yanmark.pets.domain.enums.Gender;
import com.yanmark.pets.domain.models.bindings.pets.PetCreateBindingModel;
import com.yanmark.pets.domain.models.services.AnimalServiceModel;
import com.yanmark.pets.domain.models.services.PetServiceModel;
import com.yanmark.pets.domain.models.services.UserServiceModel;
import com.yanmark.pets.repositories.PetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {
	
	private final ModelMapper modelMapper;
	private final PetRepository petRepository;
	private final AnimalService animalService;
	private final UserService userService;
	private final CloudinaryService cloudinaryService;
	
	@Autowired
	public PetServiceImpl(ModelMapper modelMapper,
	                      PetRepository petRepository,
	                      AnimalService animalService,
	                      UserService userService,
	                      CloudinaryService cloudinaryService) {
		this.modelMapper = modelMapper;
		this.petRepository = petRepository;
		this.animalService = animalService;
		this.userService = userService;
		this.cloudinaryService = cloudinaryService;
	}
	
	@Override
	public PetServiceModel savePet(PetServiceModel petService,
	                               PetCreateBindingModel petCreate,
	                               Principal principal) throws IOException {
		UserServiceModel userServiceModel = this.userService.getUserByUsername(principal.getName());
		petService.setOwner(userServiceModel);
		
		AnimalServiceModel animalServiceModel = this.animalService.getAnimalById(petCreate.getAnimal());
		petService.setAnimal(animalServiceModel);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate birthDate = LocalDate.parse(petCreate.getBirthDate(), formatter);
		petService.setBirthDate(birthDate);
		
		if (!petCreate.getVaccineDate().equals("")) {
			LocalDate vaccineDate = LocalDate.parse(petCreate.getVaccineDate(), formatter);
			petService.setVaccineDate(vaccineDate);
		}
		
		petService.setImage(this.cloudinaryService.uploadImage(petCreate.getImage()));
		
		if (petCreate.getGender().equals("male")) {
			petService.setGender(Gender.MALE);
		} else {
			petService.setGender(Gender.FEMALE);
		}
		if (petCreate.getIsCastrated().equals("true")) {
			petService.setCastrated(true);
		}
		Pet pet = this.modelMapper.map(petService, Pet.class);
		try {
			pet = this.petRepository.save(pet);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return this.modelMapper.map(pet, PetServiceModel.class);
	}
	
	@Override
	public PetServiceModel updatePet(PetServiceModel petService, PetCreateBindingModel petCreate) {
		PetServiceModel petServiceModel = this.modelMapper.map(petCreate, PetServiceModel.class);
		if (petServiceModel.getAnimal() != null) {
			petService.setAnimal(petServiceModel.getAnimal());
		}
		if (!petServiceModel.getName().equals(petService.getName())) {
			petService.setName(petServiceModel.getName());
		}
		if (petServiceModel.getBirthDate() != null) {
			petService.setBirthDate(petServiceModel.getBirthDate());
		}
		if (petServiceModel.getVaccineDate() != null) {
			petService.setVaccineDate(petServiceModel.getVaccineDate());
		}
		if (!petServiceModel.getFurColor().equals(petService.getFurColor())) {
			petService.setFurColor(petServiceModel.getFurColor());
		}
		if (!petServiceModel.getBreed().equals(petService.getBreed())) {
			petService.setBreed(petServiceModel.getBreed());
		}
		if (petServiceModel.getGender() != null) {
			petService.setGender(petServiceModel.getGender());
		}
		if (petServiceModel.isCastrated()) {
			petService.setCastrated(true);
		}
		Pet pet = this.modelMapper.map(petService, Pet.class);
		try {
			pet = this.petRepository.save(pet);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return this.modelMapper.map(pet, PetServiceModel.class);
	}
	
	@Override
	public PetServiceModel addIllnesses(PetServiceModel petService) {
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
	
	@Override
	public PetServiceModel getPetById(String id) {
		Pet pet = this.petRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Pet was not found!"));
		return this.modelMapper.map(pet, PetServiceModel.class);
	}
	
	@Override
	public void deletePet(String id) {
		try {
			this.petRepository.deleteById(id);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
}
