package com.yanmark.pets.domain.models.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IllnessServiceModel extends BaseServiceModel {
	
	private LocalDate date;
	private String name;
	private String description;
	private List<ImageServiceModel> images;
	private HealthyServiceModel healthy;
	
	public IllnessServiceModel() {
		this.images = new ArrayList<>();
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
	
	public List<ImageServiceModel> getImages() {
		return images;
	}
	
	public void setImages(List<ImageServiceModel> images) {
		this.images = images;
	}
	
	public HealthyServiceModel getHealthy() {
		return healthy;
	}
	
	public void setHealthy(HealthyServiceModel healthy) {
		this.healthy = healthy;
	}
}
