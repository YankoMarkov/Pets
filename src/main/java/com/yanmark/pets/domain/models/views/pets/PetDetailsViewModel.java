package com.yanmark.pets.domain.models.views.pets;

import com.yanmark.pets.domain.models.services.PetServiceModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class PetDetailsViewModel {

  private static final String CASTRATED = " is castrated.";
  private static final String NOT_CASTRATED = " is not castrated.";
  private static final String NOT_BEEN_VACCINATED = " not vaccinated.";

  private String id;
  private String image;
  private String name;
  private String animal;
  private int ageInYears;
  private int ageInMonths;
  private String gender;
  private String breed;
  private String furColor;
  private String isCastrated;
  private String vaccineDate;
  private String nextVaccineDate;

  public PetDetailsViewModel() {}

  public PetDetailsViewModel(PetServiceModel petService) {
    setId(petService.getId());
    setImage(petService.getImage());
    setName(petService.getName());
    setAnimal(petService.getAnimal().getName());
    setAgeInYears(petService);
    setAgeInMonths(petService);
    setGender(petService.getGender().getGender());
    setBreed(petService.getBreed());
    setFurColor(petService.getFurColor());
    setIsCastrated(petService);
    setVaccineDate(petService);
    setNextVaccineDate(petService);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAnimal() {
    return animal;
  }

  public void setAnimal(String animal) {
    this.animal = animal;
  }

  public int getAgeInYears() {
    return ageInYears;
  }

  private void setAgeInYears(PetServiceModel petService) {
    long age =
        ChronoUnit.MONTHS.between(
            petService.getBirthDate().withDayOfMonth(1), LocalDate.now().withDayOfMonth(1));
    this.ageInYears = (int) age / 12;
  }

  public int getAgeInMonths() {
    return ageInMonths;
  }

  private void setAgeInMonths(PetServiceModel petService) {
    long age =
        ChronoUnit.MONTHS.between(
            petService.getBirthDate().withDayOfMonth(1), LocalDate.now().withDayOfMonth(1));
    this.ageInMonths = (int) age % 12;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getBreed() {
    return breed;
  }

  public void setBreed(String breed) {
    this.breed = breed;
  }

  public String getFurColor() {
    return furColor;
  }

  public void setFurColor(String furColor) {
    this.furColor = furColor;
  }

  public String getIsCastrated() {
    return isCastrated;
  }

  private void setIsCastrated(PetServiceModel petService) {
    if (petService.isCastrated()) {
      this.isCastrated = petService.getName() + CASTRATED;
    } else {
      this.isCastrated = petService.getName() + NOT_CASTRATED;
    }
  }

  public String getVaccineDate() {
    return vaccineDate;
  }

  private void setVaccineDate(PetServiceModel petService) {
    if (petService.getVaccineDate() != null) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
      this.vaccineDate = petService.getVaccineDate().format(formatter);
    } else {
      this.vaccineDate = NOT_BEEN_VACCINATED;
    }
  }

  public String getNextVaccineDate() {
    return nextVaccineDate;
  }

  private void setNextVaccineDate(PetServiceModel petService) {
    this.nextVaccineDate = "";
    if (petService.getVaccineDate() != null) {
      LocalDate nextVaccine = petService.getVaccineDate().plusYears(1);
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
      this.nextVaccineDate = nextVaccine.format(formatter);
    }
  }
}
