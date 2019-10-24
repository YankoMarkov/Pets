package com.yanmark.pets.domain.models.services;

import com.yanmark.pets.domain.entities.Illness;
import com.yanmark.pets.domain.entities.Pet;
import com.yanmark.pets.domain.enums.Gender;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PetServiceModel extends BaseServiceModel {
	
	private ModelMapper modelMapper;
	
	private String image;
	private AnimalServiceModel animal;
	private String name;
	private LocalDate birthDate;
	private String breed;
	private String furColor;
	private Gender gender;
	private boolean isCastrated;
	private LocalDate vaccineDate;
	private Set<IllnessServiceModel> illnesses;
	private UserServiceModel owner;
	
	public PetServiceModel() {
		this.illnesses = new HashSet<>();
	}
	
	public PetServiceModel(Pet pet) {
		this.modelMapper = new ModelMapper();
		this.image = pet.getImage();
		this.animal = this.modelMapper.map(pet.getAnimal(), AnimalServiceModel.class);
		this.name = pet.getName();
		this.birthDate = pet.getBirthDate();
		this.breed = pet.getBreed();
		this.furColor = pet.getFurColor();
		this.gender = pet.getGender();
		this.isCastrated = pet.isCastrated();
		this.vaccineDate = pet.getVaccineDate();
		this.illnesses = getIllnessesService(pet.getIllnesses());
		this.owner = this.modelMapper.map(pet.getOwner(), UserServiceModel.class);
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public AnimalServiceModel getAnimal() {
		return animal;
	}
	
	public void setAnimal(AnimalServiceModel animal) {
		this.animal = animal;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
	
	public boolean isCastrated() {
		return isCastrated;
	}
	
	public void setCastrated(boolean castrated) {
		isCastrated = castrated;
	}
	
	public LocalDate getVaccineDate() {
		return vaccineDate;
	}
	
	public void setVaccineDate(LocalDate vaccineDate) {
		this.vaccineDate = vaccineDate;
	}
	
	public Set<IllnessServiceModel> getIllnesses() {
		return illnesses;
	}
	
	public void setIllnesses(Set<IllnessServiceModel> illnesses) {
		this.illnesses = illnesses;
	}
	
	public UserServiceModel getOwner() {
		return owner;
	}
	
	public void setOwner(UserServiceModel owner) {
		this.owner = owner;
	}
	
	private Set<IllnessServiceModel> getIllnessesService(Set<Illness> illnesses) {
		return illnesses.stream()
				.map(illness -> this.modelMapper.map(illness, IllnessServiceModel.class))
				.collect(Collectors.toSet());
	}
}
