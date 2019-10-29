package com.yanmark.pets.domain.models.views.users;

import com.yanmark.pets.domain.models.services.UserRoleServiceModel;
import com.yanmark.pets.domain.models.services.UserServiceModel;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserAllViewModel {
	
	private String id;
	private String username;
	private String email;
	private Set<String> authorities;
	
	public UserAllViewModel() {
		this.authorities = new HashSet<>();
	}
	
	public UserAllViewModel(UserServiceModel userService) {
		setId(userService.getId());
		setUsername(userService.getUsername());
		setEmail(userService.getEmail());
		setAuthorities(userService);
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Set<String> getAuthorities() {
		return authorities;
	}
	
	private void setAuthorities(UserServiceModel userService) {
		this.authorities = new HashSet<>();
		this.authorities = userService.getAuthorities().stream()
				.map(UserRoleServiceModel::getAuthority)
				.collect(Collectors.toSet());
	}
}
