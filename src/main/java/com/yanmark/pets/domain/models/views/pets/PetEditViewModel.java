package com.yanmark.pets.domain.models.views.pets;

import com.yanmark.pets.domain.models.services.PetServiceModel;
import org.springframework.web.multipart.MultipartFile;

import java.time.format.DateTimeFormatter;

public class PetEditViewModel {
	
	private String id;
	private MultipartFile image;
	private String animal;
	private String name;
	private String birthDate;
	private String breed;
	private String furColor;
	private String gender;
	private String isCastrated;
	private String vaccineDate;
	
	public PetEditViewModel() {
	}
	
	public PetEditViewModel(PetServiceModel petService) {
		setId(petService.getId());
		setImage(null);
		setAnimal(petService.getAnimal().getName());
		setName(petService.getName());
		setBirthDate(petService);
		setBreed(petService.getBreed());
		setFurColor(petService.getFurColor());
		setGender(petService.getGender().getGender());
		setIsCastrated(null);
		setVaccineDate(petService);
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public MultipartFile getImage() {
		return image;
	}
	
	public void setImage(MultipartFile image) {
		this.image = image;
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
	
	private void setBirthDate(PetServiceModel petService) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
		this.birthDate = petService.getBirthDate().format(formatter);
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
	
	public void setVaccineDate(PetServiceModel petService) {
		this.vaccineDate = "";
		if (petService.getVaccineDate() != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
			this.vaccineDate = petService.getVaccineDate().format(formatter);
		}
	}
}
