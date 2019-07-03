package com.yanmark.pets.domain.models.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IllnessServiceModel extends BaseServiceModel {
	
	private String name;
	private List<ImageServiceModel> images;
	private LocalDate startIllness;
	private String description;
	private HealthyServiceModel healthy;
	
	public IllnessServiceModel() {
		this.images = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<ImageServiceModel> getImages() {
		return images;
	}
	
	public void setImages(List<ImageServiceModel> images) {
		this.images = images;
	}
	
	public LocalDate getStartIllness() {
		return startIllness;
	}
	
	public void setStartIllness(LocalDate startIllness) {
		this.startIllness = startIllness;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public HealthyServiceModel getHealthy() {
		return healthy;
	}
	
	public void setHealthy(HealthyServiceModel healthy) {
		this.healthy = healthy;
	}
}
