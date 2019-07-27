package com.yanmark.pets.domain.entities;

import com.yanmark.pets.domain.enums.Gender;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "pets")
public class Pet extends BaseEntity {
	
	private String image;
	private Animal animal;
	private String name;
	private LocalDate birthDate;
	private String breed;
	private String furColor;
	private Gender gender;
	private Health health;
	private User owner;
	
	@Column(name = "image")
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	@OneToOne(targetEntity = Animal.class)
	@JoinColumn(name = "animal_id", referencedColumnName = "id")
	public Animal getAnimal() {
		return animal;
	}
	
	public void setAnimal(Animal animal) {
		this.animal = animal;
	}
	
	@Column(name = "name", nullable = false, unique = true)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "birth_date", nullable = false)
	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	@Column(name = "breed", nullable = false)
	public String getBreed() {
		return breed;
	}
	
	public void setBreed(String breed) {
		this.breed = breed;
	}
	
	@Column(name = "fur_color")
	public String getFurColor() {
		return furColor;
	}
	
	public void setFurColor(String furColor) {
		this.furColor = furColor;
	}
	
	@Column(name = "gender", nullable = false)
	@Enumerated(EnumType.STRING)
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	@OneToOne(targetEntity = Health.class)
	@JoinColumn(name = "healthy_id", referencedColumnName = "id")
	public Health getHealth() {
		return health;
	}
	
	public void setHealth(Health health) {
		this.health = health;
	}
	
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	public User getOwner() {
		return owner;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}
}
