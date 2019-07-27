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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {
	
	private static String UPLOADED_FOLDER = "src/main/resources/static/images/";
	private final ModelMapper modelMapper;
	private final PetRepository petRepository;
	private final AnimalService animalService;
	private final UserService userService;
	
	@Autowired
	public PetServiceImpl(ModelMapper modelMapper,
	                      PetRepository petRepository,
	                      AnimalService animalService,
	                      UserService userService) {
		this.modelMapper = modelMapper;
		this.petRepository = petRepository;
		this.animalService = animalService;
		this.userService = userService;
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
		LocalDate date = LocalDate.parse(petCreate.getBirthDate(), formatter);
		petService.setBirthDate(date);
		Path path = Paths.get(UPLOADED_FOLDER, petCreate.getImage().getOriginalFilename());
		petCreate.getImage().transferTo(path);
		petService.setImage("/images/" + petCreate.getImage().getOriginalFilename());
		
		if (petCreate.getGender().equals("male")) {
			petService.setGender(Gender.MALE);
		} else {
			petService.setGender(Gender.FEMALE);
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
