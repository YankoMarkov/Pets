package com.yanmark.pets.web.controllers;

import com.yanmark.pets.domain.models.bindings.pets.PetCreateBindingModel;
import com.yanmark.pets.domain.models.bindings.pets.PetEditBindingModel;
import com.yanmark.pets.domain.models.services.PetServiceModel;
import com.yanmark.pets.domain.models.views.pets.PetDetailsViewModel;
import com.yanmark.pets.domain.models.views.pets.PetEditViewModel;
import com.yanmark.pets.services.PetService;
import com.yanmark.pets.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
		if (petCreate.getImage().isEmpty()) {
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
	
	@GetMapping("/details/{id}")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView details(@PathVariable String id,
	                            ModelAndView modelAndView) {
		PetServiceModel petServiceModel = this.petService.getPetById(id);
		PetDetailsViewModel petDetailsViewModel = this.modelMapper.map(petServiceModel, PetDetailsViewModel.class);
		long age = ChronoUnit.MONTHS.between(
				petServiceModel.getBirthDate().withDayOfMonth(1),
				LocalDate.now().withDayOfMonth(1));
		petDetailsViewModel.setAgeInYears((int) age / 12);
		petDetailsViewModel.setAgeInMonths((int) age % 12);
		petDetailsViewModel.setGender(petServiceModel.getGender().getGender());
		petDetailsViewModel.setAnimal(petServiceModel.getAnimal().getName());
		if (petServiceModel.isCastrated()) {
			petDetailsViewModel.setIsCastrated(petServiceModel.getName() + " is castrated.");
		} else {
			petDetailsViewModel.setIsCastrated(petServiceModel.getName() + " is not castrated.");
		}
		if (petServiceModel.getVaccineDate() != null) {
			LocalDate nextVaccine = petServiceModel.getVaccineDate().plusYears(1);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
			String date = petServiceModel.getVaccineDate().format(formatter);
			String nextDate = nextVaccine.format(formatter);
			petDetailsViewModel.setVaccineDate(date);
			petDetailsViewModel.setNextVaccineDate(nextDate);
		} else {
			petDetailsViewModel.setVaccineDate(petServiceModel.getName() + " has not been vaccinated.");
		}
		modelAndView.addObject("vaccineDateExpired", this.petService.vaccineDateExpired(id));
		modelAndView.addObject("pet", petDetailsViewModel);
		return this.view("/pets/pet-details", modelAndView);
	}
	
	@GetMapping("/edit/{id}")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView edit(@PathVariable String id,
	                         ModelAndView modelAndView) {
		PetServiceModel petServiceModel = this.petService.getPetById(id);
		PetEditViewModel petEditViewModel = this.modelMapper.map(petServiceModel, PetEditViewModel.class);
		petEditViewModel.setAnimal(petServiceModel.getAnimal().getName());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
		String birthDate = petServiceModel.getBirthDate().format(formatter);
		petEditViewModel.setBirthDate(birthDate);
		if (petServiceModel.getVaccineDate() != null) {
			String vaccineDate = petServiceModel.getVaccineDate().format(formatter);
			petEditViewModel.setVaccineDate(vaccineDate);
		}
		petEditViewModel.setGender(petServiceModel.getGender().getGender());
		modelAndView.addObject("pet", petEditViewModel);
		return view("/pets/edit-pet", modelAndView);
	}
	
	@PostMapping("/edit/{id}")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView editConfirm(@ModelAttribute("petEdit") PetEditBindingModel petEdit,
	                                @PathVariable String id,
	                                BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return view("/pets/edit-pet");
		}
		PetServiceModel petServiceModel = this.petService.getPetById(id);
		this.petService.updatePet(petServiceModel, petEdit);
		return redirect("/home");
	}
	
	@GetMapping("/delete/{id}")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView delete(@PathVariable String id) {
		this.petService.deletePet(id);
		return redirect("/home");
	}
}
