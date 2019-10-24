package com.yanmark.pets.domain.models.services;

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
		setImage(pet.getImage());
		setAnimal(pet);
		setName(pet.getName());
		setBirthDate(pet.getBirthDate());
		setBreed(pet.getBreed());
		setFurColor(pet.getFurColor());
		setGender(pet.getGender());
		setCastrated(pet.isCastrated());
		setVaccineDate(pet.getVaccineDate());
		setIllnesses(pet);
		setOwner(pet);
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
	
	public void setAnimal(Pet pet) {
		this.animal = this.modelMapper.map(pet.getAnimal(), AnimalServiceModel.class);
		;
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
	
	public void setIllnesses(Pet pet) {
		this.illnesses = pet.getIllnesses().stream()
				.map(illness -> this.modelMapper.map(illness, IllnessServiceModel.class))
				.collect(Collectors.toSet());
	}
	
	public UserServiceModel getOwner() {
		return owner;
	}
	
	public void setOwner(Pet pet) {
		this.owner = this.modelMapper.map(pet.getOwner(), UserServiceModel.class);
	}
}
