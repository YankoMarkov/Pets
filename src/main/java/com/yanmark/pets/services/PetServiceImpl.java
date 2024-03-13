package com.yanmark.pets.services;

import com.yanmark.pets.domain.entities.Animal;
import com.yanmark.pets.domain.entities.Pet;
import com.yanmark.pets.domain.entities.User;
import com.yanmark.pets.domain.enums.Gender;
import com.yanmark.pets.domain.models.bindings.pets.PetCreateBindingModel;
import com.yanmark.pets.domain.models.bindings.pets.PetEditBindingModel;
import com.yanmark.pets.domain.models.services.AnimalServiceModel;
import com.yanmark.pets.domain.models.services.PetServiceModel;
import com.yanmark.pets.domain.models.services.UserServiceModel;
import com.yanmark.pets.repositories.PetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PetServiceImpl implements PetService {

  private static final String PET_NOT_FOUND = "Pet was not found!";
  private static final String PET_FIELDS_IS_WRONG = "Check the required fields and image file!";

  private final ModelMapper modelMapper;
  private final PetRepository petRepository;
  private final AnimalService animalService;
  private final UserService userService;
  private final CloudinaryService cloudinaryService;
  private final PetValidationService petValidation;
  private int page = 0;
  private int size = 10;

  @Autowired
  public PetServiceImpl(
      ModelMapper modelMapper,
      PetRepository petRepository,
      AnimalService animalService,
      UserService userService,
      CloudinaryService cloudinaryService,
      PetValidationService petValidation) {
    this.modelMapper = modelMapper;
    this.petRepository = petRepository;
    this.animalService = animalService;
    this.userService = userService;
    this.cloudinaryService = cloudinaryService;
    this.petValidation = petValidation;
  }

  @Override
  public PetServiceModel savePet(PetCreateBindingModel petCreate, Principal principal)
      throws IOException {
    if (petValidation.isValidStrings(
        petCreate.getImage(),
        petCreate.getAnimal(),
        petCreate.getName(),
        petCreate.getBirthDate(),
        petCreate.getGender(),
        petCreate.getIsCastrated())) {
      PetServiceModel petService = this.modelMapper.map(petCreate, PetServiceModel.class);
      UserServiceModel userServiceModel = this.userService.getUserByUsername(principal.getName());
      petService.setOwner(userServiceModel);

      AnimalServiceModel animalServiceModel =
          this.animalService.getAnimalById(petCreate.getAnimal());
      petService.setAnimal(animalServiceModel);

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate birthDate = LocalDate.parse(petCreate.getBirthDate(), formatter);
      petService.setBirthDate(birthDate);

      if (!petCreate.getVaccineDate().isBlank()) {
        LocalDate vaccineDate = LocalDate.parse(petCreate.getVaccineDate(), formatter);
        petService.setVaccineDate(vaccineDate);
      }
      String image = this.cloudinaryService.uploadImage(petCreate.getImage());
      petService.setImage(image);

      if (petCreate.getGender().equals("male")) {
        petService.setGender(Gender.MALE);
      } else {
        petService.setGender(Gender.FEMALE);
      }
      if (petCreate.getIsCastrated().equals("true")) {
        petService.setCastrated(true);
      }
      return savePetToDB(petService);
    } else {
      throw new IllegalArgumentException(PET_FIELDS_IS_WRONG);
    }
  }

  @Override
  public PetServiceModel updatePet(PetServiceModel petService, PetEditBindingModel petEdit)
      throws IOException {
    DateTimeFormatter stringFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    String castrated = petService.isCastrated() ? "true" : "false";
    
    if (!petEdit.getImage().isEmpty()) {
      petService.setImage(this.cloudinaryService.uploadImage(petEdit.getImage()));
    }
    String birthDate = petService.getBirthDate().format(stringFormatter);
    String vaccineDate = "";

    if (petService.getVaccineDate() != null) {
      vaccineDate = petService.getVaccineDate().format(stringFormatter);
    }
    if (!petEdit.getAnimal().isBlank()
        && !petService.getAnimal().getId().equals(petEdit.getAnimal())) {
      AnimalServiceModel animalServiceModel = this.animalService.getAnimalById(petEdit.getAnimal());
      petService.setAnimal(animalServiceModel);
    }
    if (!petEdit.getName().isBlank()
        && !petEdit.getName().trim().equalsIgnoreCase(petService.getName())) {
      petService.setName(petEdit.getName());
    }
    if (!petEdit.getBirthDate().isBlank()
        && !birthDate.equals(petEdit.getBirthDate())) {
      LocalDate date = LocalDate.parse(petEdit.getBirthDate(), dateFormatter);
      petService.setBirthDate(date);
    }
    if (!petEdit.getVaccineDate().isBlank()
        && !vaccineDate.equals(petEdit.getVaccineDate())) {
      LocalDate date = LocalDate.parse(petEdit.getVaccineDate(), dateFormatter);
      petService.setVaccineDate(date);
    }
    if (!petEdit.getFurColor().isBlank()
        && !petEdit.getFurColor().equalsIgnoreCase(petService.getFurColor())) {
      petService.setFurColor(petEdit.getFurColor());
    }
    if (!petEdit.getBreed().isBlank()
        && !petEdit.getBreed().equalsIgnoreCase(petService.getBreed())) {
      petService.setBreed(petEdit.getBreed());
    }
    if (petEdit.getGender() != null
        && !petEdit.getGender().equalsIgnoreCase(petService.getGender().toString())) {
      if (petEdit.getGender().equals("male")) {
        petService.setGender(Gender.MALE);
      } else {
        petService.setGender(Gender.FEMALE);
      }
    }
    if (petEdit.getIsCastrated() != null && !castrated.equals(petEdit.getIsCastrated())) {
	    petService.setCastrated(petEdit.getIsCastrated().equals("true"));
    }
    return savePetToDB(petService);
  }

  @Override
  public PetServiceModel addIllness(PetServiceModel petService) {
    return savePetToDB(petService);
  }

  @Override
  public Page<PetServiceModel> getAllPets(HttpServletRequest request) {
    createPage(request);
    Page<Pet> pets = this.petRepository.getAllByOrderByBirthDateDesc(PageRequest.of(page, size));
    return pets.map(PetServiceModel::new);
  }

  @Override
  public Page<PetServiceModel> getAllPetsByAnimal(
      AnimalServiceModel animalService, HttpServletRequest request) {
    Animal animal = this.modelMapper.map(animalService, Animal.class);
    createPage(request);
    Page<Pet> pets =
        this.petRepository.getAllByAnimalOrderByBirthDateDesc(animal, PageRequest.of(page, size));
    return pets.map(PetServiceModel::new);
  }

  @Override
  public Page<PetServiceModel> getAllPetsByOwner(
      UserServiceModel userService, HttpServletRequest request) {
    User owner = this.modelMapper.map(userService, User.class);
    createPage(request);
    Page<Pet> pets =
        this.petRepository.getAllByOwnerOrderByBirthDateDesc(owner, PageRequest.of(page, size));
    return pets.map(PetServiceModel::new);
  }

  @Override
  public Page<PetServiceModel> getAllPetsByOwnerAndAnimal(
      UserServiceModel userService, AnimalServiceModel animalService, HttpServletRequest request) {
    User owner = this.modelMapper.map(userService, User.class);
    Animal animal = this.modelMapper.map(animalService, Animal.class);
    createPage(request);
    Page<Pet> pets =
        this.petRepository.getAllByOwnerAndAnimalOrderByBirthDateDesc(
            owner, animal, PageRequest.of(page, size));
    return pets.map(PetServiceModel::new);
  }

  @Override
  public PetServiceModel getPetByName(String name) {
    Pet pet =
        this.petRepository
            .getByName(name)
            .orElseThrow(() -> new IllegalArgumentException(PET_NOT_FOUND));
    return this.modelMapper.map(pet, PetServiceModel.class);
  }

  @Override
  public PetServiceModel getPetById(String id) {
    Pet pet =
        this.petRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException(PET_NOT_FOUND));
    return this.modelMapper.map(pet, PetServiceModel.class);
  }

  @Override
  public Boolean vaccineDateExpired(String id) {
    LocalDate date = LocalDate.now();
    Pet pet =
        this.petRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException(PET_NOT_FOUND));
	  return pet.getVaccineDate() != null && date.isAfter(pet.getVaccineDate().plusYears(1));
  }

  @Override
  public void deletePet(String id) {
    try {
      this.petRepository.deleteById(id);
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  private PetServiceModel savePetToDB(PetServiceModel petService) {
    Pet pet = this.modelMapper.map(petService, Pet.class);
    try {
      pet = this.petRepository.save(pet);
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    return this.modelMapper.map(pet, PetServiceModel.class);
  }

  private void createPage(HttpServletRequest request) {
    if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
      page = Integer.parseInt(request.getParameter("page")) - 1;
    }
    if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
      size = Integer.parseInt(request.getParameter("size"));
    }
  }
}
