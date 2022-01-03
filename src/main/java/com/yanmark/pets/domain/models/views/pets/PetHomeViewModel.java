package com.yanmark.pets.domain.models.views.pets;

import com.yanmark.pets.domain.models.services.PetServiceModel;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PetHomeViewModel {

  private String id;
  private String image;
  private String animal;
  private String name;
  private int ageInYears;
  private int ageInMonths;
  private String gender;

  public PetHomeViewModel() {}

  public PetHomeViewModel(PetServiceModel petService) {
    setId(petService.getId());
    setImage(petService.getImage());
    setAnimal(petService.getAnimal().getName());
    setName(petService.getName());
    setAgeInYears(petService);
    setAgeInMonths(petService);
    setGender(petService.getGender().getGender());
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

  public String getAnimal() {
    return animal;
  }

  public void setAnimal(String animal) {
    this.animal = animal;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
}
