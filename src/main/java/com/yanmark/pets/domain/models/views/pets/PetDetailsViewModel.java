package com.yanmark.pets.domain.models.views.pets;

public class PetDetailsViewModel {
	
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
	
	public void setAgeInYears(int ageInYears) {
		this.ageInYears = ageInYears;
	}
	
	public int getAgeInMonths() {
		return ageInMonths;
	}
	
	public void setAgeInMonths(int ageInMonths) {
		this.ageInMonths = ageInMonths;
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
	
	public void setIsCastrated(String isCastrated) {
		this.isCastrated = isCastrated;
	}
	
	public String getVaccineDate() {
		return vaccineDate;
	}
	
	public void setVaccineDate(String vaccineDate) {
		this.vaccineDate = vaccineDate;
	}
	
	public String getNextVaccineDate() {
		return nextVaccineDate;
	}
	
	public void setNextVaccineDate(String nextVaccineDate) {
		this.nextVaccineDate = nextVaccineDate;
	}
}