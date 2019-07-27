package com.yanmark.pets.domain.models.bindings.animals;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AnimalCreateBindingModel {
	
	private String name;
	
	@NotNull(message = "Name cannot be null.")
	@Size(min = 2, max = 15, message = "Name must be in range [2 - 15] symbols.")
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
