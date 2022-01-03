package com.yanmark.pets.domain.models.services;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class IllnessServiceModel extends BaseServiceModel {

  private LocalDate date;
  private String name;
  private String description;
  private Set<ImageServiceModel> images;
  private PetServiceModel pet;

  public IllnessServiceModel() {
    this.images = new HashSet<>();
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<ImageServiceModel> getImages() {
    return images;
  }

  public void setImages(Set<ImageServiceModel> images) {
    this.images = images;
  }

  public PetServiceModel getPet() {
    return pet;
  }

  public void setPet(PetServiceModel pet) {
    this.pet = pet;
  }
}
