package com.yanmark.pets.domain.models.bindings.illnesses;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class IllnessCreateBindingModel {
	
	private String date;
	private String name;
	private String description;
	private MultipartFile image;
	
	@NotNull(message = "Date cannot be null.")
	@NotBlank(message = "Date cannot be empty.")
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	@NotNull(message = "Name cannot be null.")
	@NotBlank(message = "Name cannot be empty.")
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
