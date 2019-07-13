package com.yanmark.pets.domain.models.views.pets;

import com.yanmark.pets.domain.entities.Animal;
import com.yanmark.pets.domain.enums.Gender;

public class PetHomeViewModel {
	
	private String id;
	private String image;
	private Animal animal;
	private String name;
	private int ageInYears;
	private int ageInMonths;
	private Gender gender;
	
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
	
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
}
