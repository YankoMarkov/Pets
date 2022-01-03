package com.yanmark.pets.domain.models.views.illnesses;

import com.yanmark.pets.domain.models.services.IllnessServiceModel;
import com.yanmark.pets.domain.models.services.ImageServiceModel;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IllnessDetailsViewModel {

  private String id;
  private String date;
  private String name;
  private String description;
  private List<String> images;

  public IllnessDetailsViewModel() {
    this.images = new ArrayList<>();
  }

  public IllnessDetailsViewModel(IllnessServiceModel illnessService) {
    setId(illnessService.getId());
    setDate(illnessService);
    setName(illnessService.getName());
    setDescription(illnessService.getDescription());
    setImages(illnessService.getImages());
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDate() {
    return date;
  }

  private void setDate(IllnessServiceModel illnessService) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    this.date = illnessService.getDate().format(formatter);
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

  public List<String> getImages() {
    return images;
  }

  private void setImages(Set<ImageServiceModel> imageServices) {
    this.images = new ArrayList<>();
    if (!imageServices.isEmpty()) {
      for (ImageServiceModel imageService : imageServices) {
        this.images.add(imageService.getImage());
      }
    }
  }
}
