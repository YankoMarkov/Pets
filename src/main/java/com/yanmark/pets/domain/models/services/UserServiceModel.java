package com.yanmark.pets.domain.models.services;

import java.util.HashSet;
import java.util.Set;

public class UserServiceModel extends BaseServiceModel {
	
	private String username;
	private String password;
	private String email;
	private Set<UserRoleServiceModel> authorities;
	private Set<PetServiceModel> pets;
	
	public UserServiceModel() {
		this.authorities = new HashSet<>();
		this.pets = new HashSet<>();
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Set<UserRoleServiceModel> getAuthorities() {
		return authorities;
	}
	
	public void setAuthorities(Set<UserRoleServiceModel> authorities) {
		this.authorities = authorities;
	}
	
	public Set<PetServiceModel> getPets() {
		return pets;
	}
	
	public void setPets(Set<PetServiceModel> pets) {
		this.pets = pets;
	}
}
