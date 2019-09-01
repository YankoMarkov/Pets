package com.yanmark.pets.domain.models.services;

import java.util.HashSet;
import java.util.Set;

public class AnimalServiceModel extends BaseServiceModel {
	
	private String name;
	private Set<PetServiceModel> pets;
	
	public AnimalServiceModel() {
		this.pets = new HashSet<>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<PetServiceModel> getPets() {
		return pets;
	}
	
	public void setPets(Set<PetServiceModel> pets) {
		this.pets = pets;
	}
}
