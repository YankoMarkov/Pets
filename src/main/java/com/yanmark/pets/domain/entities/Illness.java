package com.yanmark.pets.domain.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "illnesses")
public class Illness extends BaseEntity {
	
	private LocalDate date;
	private String name;
	private String description;
	private List<Image> images;
	private Healthy healthy;
	
	public Illness() {
		this.images = new ArrayList<>();
	}
	
	@Column(name = "date", nullable = false)
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "description")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToMany(targetEntity = Image.class, mappedBy = "illness")
	public List<Image> getImages() {
		return images;
	}
	
	public void setImages(List<Image> images) {
		this.images = images;
	}
	
	@ManyToOne(targetEntity = Healthy.class)
	@JoinColumn(name = "healthy_id", referencedColumnName = "id")
	public Healthy getHealthy() {
		return healthy;
	}
	
	public void setHealthy(Healthy healthy) {
		this.healthy = healthy;
	}
}
