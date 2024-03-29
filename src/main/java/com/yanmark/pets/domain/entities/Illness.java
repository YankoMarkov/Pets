package com.yanmark.pets.domain.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "illnesses")
public class Illness extends BaseEntity {
	
	private LocalDate date;
	private String name;
	private String description;
	private Set<Image> images;
	private Pet pet;
	
	public Illness() {
		this.images = new HashSet<>();
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
	
	@OneToMany(targetEntity = Image.class, mappedBy = "illness", cascade = CascadeType.ALL)
	public Set<Image> getImages() {
		return images;
	}
	
	public void setImages(Set<Image> images) {
		this.images = images;
	}
	
	@ManyToOne(targetEntity = Pet.class)
	@JoinColumn(name = "pet_id", referencedColumnName = "id")
	public Pet getPet() {
		return pet;
	}
	
	public void setPet(Pet pet) {
		this.pet = pet;
	}
}
