package com.yanmark.pets.domain.models.services;

public class ImageServiceModel extends BaseServiceModel {
	
	private String image;
	private IllnessServiceModel illness;
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public IllnessServiceModel getIllness() {
		return illness;
	}
	
	public void setIllness(IllnessServiceModel illness) {
		this.illness = illness;
	}
}
