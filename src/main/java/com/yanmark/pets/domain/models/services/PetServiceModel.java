package com.yanmark.pets.domain.models.services;

import com.yanmark.pets.domain.enums.Animal;
import com.yanmark.pets.domain.enums.Gender;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PetServiceModel extends BaseServiceModel {
	
	private Animal animal;
	private String name;
	private List<ImageServiceModel> images;
	private LocalDate birthDate;
	private String breed;
	private String furColor;
	private Gender gender;
	private HealthyServiceModel healthy;
	private UserServiceModel owner;
	
	public PetServiceModel() {
		this.images = new ArrayList<>();
	}
	
	public Animal getAnimal() {
		return animal;
	}
	
	public void setAnimal(Animal animal) {
		this.animal = animal;
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
	
	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
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
	
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public HealthyServiceModel getHealthy() {
		return healthy;
	}
	
	public void setHealthy(HealthyServiceModel healthy) {
		this.healthy = healthy;
	}
	
	public UserServiceModel getOwner() {
		return owner;
	}
	
	public void setOwner(UserServiceModel owner) {
		this.owner = owner;
	}
}
