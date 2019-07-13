package com.yanmark.pets.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "animals")
public class Animal extends BaseEntity {
	
	private String name;
	
	@Column(name = "name", nullable = false, unique = true)
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
