package com.yanmark.pets.domain.models.views.illnesses;

import java.util.ArrayList;
import java.util.List;

public class IllnessDetailsViewModel {
	
	private String id;
	private String date;
	private String name;
	private String description;
	private List<String> images;
	
	public IllnessDetailsViewModel() {
		this.images = new ArrayList<>();
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
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
	
	public List<String> getImages() {
		return images;
	}
	
	public void setImages(List<String> images) {
		this.images = images;
	}
}
