package com.yanmark.pets.domain.models.bindings.pets;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PetCreateBindingModel {
	
	private MultipartFile image;
	private String animal;
	private String name;
	private String birthDate;
	private String breed;
	private String furColor;
	private String gender;
	private String isCastrated;
	private String vaccineDate;
	
	public MultipartFile getImage() {
		return image;
	}
	
	public void setImage(MultipartFile image) {
		this.image = image;
	}
	
	@NotNull(message = "Animal cannot be null.")
	@NotBlank(message = "Animal cannot be empty.")
	public String getAnimal() {
		return animal;
	}
	
	public void setAnimal(String animal) {
		this.animal = animal;
	}
	
	@NotNull(message = "Name cannot be null.")
	@Size(min = 3, max = 30, message = "Name must be in range [3 - 30] symbols.")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message = "Date cannot be null.")
	@NotBlank(message = "Date cannot be empty.")
	public String getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
	@NotNull(message = "Breed cannot be null.")
	@NotBlank(message = "Breed cannot be empty.")
	public String getBreed() {
		return breed;
	}
	
	public void setBreed(String breed) {
		this.breed = breed;
	}
	
	@NotNull(message = "Fur Color cannot be null.")
	@NotBlank(message = "Fur Color cannot be empty.")
	public String getFurColor() {
		return furColor;
	}
	
	public void setFurColor(String furColor) {
		this.furColor = furColor;
	}
	
	@NotNull(message = "Gender cannot be null.")
	@NotBlank(message = "Gender cannot be empty.")
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@NotNull(message = "Is castrated cannot be null.")
	@NotBlank(message = "Is castrated cannot be empty.")
	public String getIsCastrated() {
		return isCastrated;
	}
	
	public void setIsCastrated(String isCastrated) {
		this.isCastrated = isCastrated;
	}
	
	public String getVaccineDate() {
		return vaccineDate;
	}
	
	public void setVaccineDate(String vaccineDate) {
		this.vaccineDate = vaccineDate;
	}
}
