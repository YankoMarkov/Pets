package com.yanmark.pets.web.controllers;

import com.yanmark.pets.domain.models.bindings.pets.PetCreateBindingModel;
import com.yanmark.pets.domain.models.services.PetServiceModel;
import com.yanmark.pets.services.PetService;
import com.yanmark.pets.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/pets")
public class PetController extends BaseController {
	
	private final PetService petService;
	private final ModelMapper modelMapper;
	private final UserService userService;
	
	@Autowired
	public PetController(PetService petService,
	                     ModelMapper modelMapper,
	                     UserService userService) {
		this.petService = petService;
		this.modelMapper = modelMapper;
		this.userService = userService;
	}
	
	@GetMapping("/add")
	@PreAuthorize("isAuthenticated()")
	ModelAndView create(@ModelAttribute("petCreate") PetCreateBindingModel petCreate) {
		return this.view("/pets/create-pet");
	}
	
	@PostMapping("/add")
	@PreAuthorize("isAuthenticated()")
	ModelAndView createConfirm(@ModelAttribute("petCreate") PetCreateBindingModel petCreate,
	                           BindingResult bindingResult,
	                           Principal principal) {
		if (bindingResult.hasErrors()) {
			return view("/pets/create-pet");
		}
		PetServiceModel petServiceModel = this.modelMapper.map(petCreate, PetServiceModel.class);
		try {
			this.petService.savePet(petServiceModel, petCreate, principal);
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return this.redirect("/home");
	}
}
