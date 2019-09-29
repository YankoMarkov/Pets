package com.yanmark.pets.services;

import com.yanmark.pets.domain.entities.User;
import com.yanmark.pets.domain.entities.UserRole;
import com.yanmark.pets.domain.models.bindings.users.UserEditBindingModel;
import com.yanmark.pets.domain.models.services.UserRoleServiceModel;
import com.yanmark.pets.domain.models.services.UserServiceModel;
import com.yanmark.pets.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
	
	private static final String USER_NOT_FOUND = "User was not found!";
	private static final String INCORRECT_PASSWORD = "Incorrect password!";
	
	private final UserRepository userRepository;
	private final UserRoleService userRoleService;
	private final ModelMapper modelMapper;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository,
	                       UserRoleService userRoleService,
	                       ModelMapper modelMapper,
	                       BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.userRoleService = userRoleService;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
	}
	
	@Override
	public UserServiceModel saveUser(UserServiceModel userService) {
		User user = this.modelMapper.map(giveRolesToUser(userService), User.class);
		user.setPassword(this.passwordEncoder.encode(userService.getPassword()));
		try {
			user = this.userRepository.save(user);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return this.modelMapper.map(user, UserServiceModel.class);
	}
	
	@Override
	public UserServiceModel updateUsersRole(UserServiceModel userService, UserRoleServiceModel userRoleService) {
		User user = this.userRepository.findById(userService.getId())
				.orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));
		try {
			UserRole userRole = this.modelMapper.map(userRoleService, UserRole.class);
			List<String> roles = user.getAuthorities().stream()
					.map(UserRole::getAuthority)
					.collect(Collectors.toList());
			if (!roles.contains(userRole.getAuthority())) {
				user.getAuthorities().add(userRole);
			}
			if (roles.contains(userRole.getAuthority())) {
				UserRole role = user.getAuthorities().stream()
						.filter(r -> r.getAuthority().equals(userRole.getAuthority()))
						.findFirst().orElse(null);
				user.getAuthorities().remove(role);
			}
			user = this.userRepository.save(user);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return this.modelMapper.map(user, UserServiceModel.class);
	}
	
	@Override
	public UserServiceModel updateUser(UserServiceModel userService, UserEditBindingModel userEdit) {
		User user = this.userRepository.findByUsername(userService.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
		if (!userEdit.getNewPassword().equals("") && userEdit.getNewPassword() != null) {
			userService.setPassword(userEdit.getNewPassword());
		}
		if (!this.passwordEncoder.matches(userEdit.getPassword(), user.getPassword())) {
			throw new IllegalArgumentException(INCORRECT_PASSWORD);
		}
		if (!this.passwordEncoder.matches(userService.getPassword(), user.getPassword())) {
			user.setPassword(this.passwordEncoder.encode(userService.getPassword()));
		}
		user.setEmail(userService.getEmail());
		try {
			user = this.userRepository.save(user);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return this.modelMapper.map(user, UserServiceModel.class);
	}
	
	@Override
	public UserServiceModel getUserById(String id) {
		return this.userRepository.findById(id)
				.map(user -> this.modelMapper.map(user, UserServiceModel.class))
				.orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));
	}
	
	@Override
	public UserServiceModel getUserByUsername(String username) {
		return this.userRepository.findByUsername(username)
				.map(user -> this.modelMapper.map(user, UserServiceModel.class))
				.orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
	}
	
	@Override
	public List<UserServiceModel> getAllUsers() {
		return this.userRepository.findAllOrderByUsername().stream()
				.map(user -> this.modelMapper.map(user, UserServiceModel.class))
				.collect(Collectors.toUnmodifiableList());
	}
	
	private UserServiceModel giveRolesToUser(UserServiceModel userService) {
		if (this.userRepository.count() == 0) {
			userService.setAuthorities(this.userRoleService.getAllRoles());
		} else {
			userService.getAuthorities().add(this.userRoleService.getRoleByName("USER"));
		}
		return userService;
	}
}
