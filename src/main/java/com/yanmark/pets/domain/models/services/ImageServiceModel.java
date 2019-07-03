package com.yanmark.pets.domain.models.services;

public class ImageServiceModel {
	
	private String image;
	private PetServiceModel pet;
	private IllnessServiceModel illness;
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public PetServiceModel getPet() {
		return pet;
	}
	
	public void setPet(PetServiceModel pet) {
		this.pet = pet;
	}
	
	public IllnessServiceModel getIllness() {
		return illness;
	}
	
	public void setIllness(IllnessServiceModel illness) {
		this.illness = illness;
	}
}
