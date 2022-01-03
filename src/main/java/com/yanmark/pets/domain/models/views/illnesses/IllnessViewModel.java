package com.yanmark.pets.domain.models.views.illnesses;

import com.yanmark.pets.domain.models.services.IllnessServiceModel;

import java.time.format.DateTimeFormatter;

public class IllnessViewModel {

  private String id;
  private String name;
  private String date;

  public IllnessViewModel() {}

  public IllnessViewModel(IllnessServiceModel illnessService) {
    setId(illnessService.getId());
    setName(illnessService.getName());
    setDate(illnessService);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDate() {
    return date;
  }

  private void setDate(IllnessServiceModel illnessService) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    this.date = illnessService.getDate().format(formatter);
  }
}
