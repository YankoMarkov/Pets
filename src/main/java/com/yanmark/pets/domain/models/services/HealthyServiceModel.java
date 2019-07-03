package com.yanmark.pets.domain.models.services;

import com.yanmark.pets.domain.entities.Pet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HealthyServiceModel extends BaseServiceModel {
	
	private boolean isCastrated;
	private LocalDate vaccineDate;
	private List<IllnessServiceModel> illnesses;
	private Pet pet;
	
	public HealthyServiceModel() {
		this.illnesses = new ArrayList<>();
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
	
	public List<IllnessServiceModel> getIllnesses() {
		return illnesses;
	}
	
	public void setIllnesses(List<IllnessServiceModel> illnesses) {
		this.illnesses = illnesses;
	}
	
	public Pet getPet() {
		return pet;
	}
	
	public void setPet(Pet pet) {
		this.pet = pet;
	}
}
