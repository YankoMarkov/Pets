package com.yanmark.pets.domain.models.views.illnesses;

import org.springframework.web.multipart.MultipartFile;

public class IllnessEditViewModel {
	
	private String id;
	private String name;
	private String description;
	private MultipartFile image;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public MultipartFile getImage() {
		return image;
	}
	
	public void setImage(MultipartFile image) {
		this.image = image;
	}
}
