package com.yanmark.pets.domain.models.views.pets;

import org.springframework.web.multipart.MultipartFile;

public class PetEditViewModel {
	
	private String id;
	private String animal;
	private String name;
	private String birthDate;
	private String breed;
	private String furColor;
	private String gender;
	private String isCastrated;
	private String vaccineDate;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
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
	
	public String getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(String birthDate) {
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
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
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
