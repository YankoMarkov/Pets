package com.yanmark.pets.web.controllers;

import com.yanmark.pets.domain.models.bindings.pets.PetCreateBindingModel;
import com.yanmark.pets.domain.models.bindings.pets.PetEditBindingModel;
import com.yanmark.pets.domain.models.services.PetServiceModel;
import com.yanmark.pets.domain.models.views.pets.PetDetailsViewModel;
import com.yanmark.pets.domain.models.views.pets.PetEditViewModel;
import com.yanmark.pets.services.PetService;
import com.yanmark.pets.services.UserService;
import com.yanmark.pets.web.annotations.PageTitle;
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
	
	private static final String HOME = "/home";
	private static final String EDIT_PET = "pets/edit-pet";
	private static final String CREATE_PET = "pets/create-pet";
	private static final String CASTRATED = " is castrated.";
	private static final String NOT_CASTRATED = " is not castrated.";
	private static final String NOT_BEEN_VACCINATED = " has not been vaccinated.";
	private static final String PET_DETAILS = "pets/pet-details";
	
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
	@PageTitle("\uD835\uDD7B\uD835\uDD8A\uD835\uDD99 \uD835\uDD6C\uD835\uDD89\uD835\uDD89")
	@PreAuthorize("isAuthenticated()")
	ModelAndView create(@ModelAttribute("petCreate") PetCreateBindingModel petCreate) {
		return this.view(CREATE_PET);
	}
	
	@PostMapping("/add")
	@PreAuthorize("isAuthenticated()")
	ModelAndView createConfirm(@ModelAttribute("petCreate") PetCreateBindingModel petCreate,
	                           BindingResult bindingResult,
	                           Principal principal) {
		if (bindingResult.hasErrors()) {
			return view(CREATE_PET);
		}
		if (petCreate.getImage().isEmpty()) {
			return view(CREATE_PET);
		}
		PetServiceModel petServiceModel = this.modelMapper.map(petCreate, PetServiceModel.class);
		try {
			this.petService.savePet(petServiceModel, petCreate, principal);
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return this.redirect(HOME);
	}
	
	@GetMapping("/details/{id}")
	@PageTitle("\uD835\uDD7B\uD835\uDD8A\uD835\uDD99 \uD835\uDD6F\uD835\uDD8A\uD835\uDD99\uD835\uDD86\uD835\uDD8E\uD835\uDD91\uD835\uDD98")
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
			petDetailsViewModel.setIsCastrated(petServiceModel.getName() + CASTRATED);
		} else {
			petDetailsViewModel.setIsCastrated(petServiceModel.getName() + NOT_CASTRATED);
		}
		if (petServiceModel.getVaccineDate() != null) {
			LocalDate nextVaccine = petServiceModel.getVaccineDate().plusYears(1);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
			String date = petServiceModel.getVaccineDate().format(formatter);
			String nextDate = nextVaccine.format(formatter);
			petDetailsViewModel.setVaccineDate(date);
			petDetailsViewModel.setNextVaccineDate(nextDate);
		} else {
			petDetailsViewModel.setVaccineDate(petServiceModel.getName() + NOT_BEEN_VACCINATED);
		}
		modelAndView.addObject("vaccineDateExpired", this.petService.vaccineDateExpired(id));
		modelAndView.addObject("pet", petDetailsViewModel);
		return this.view(PET_DETAILS, modelAndView);
	}
	
	@GetMapping("/edit/{id}")
	@PageTitle("\uD835\uDD7B\uD835\uDD8A\uD835\uDD99 \uD835\uDD70\uD835\uDD89\uD835\uDD8E\uD835\uDD99")
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
		return view(EDIT_PET, modelAndView);
	}
	
	@PostMapping("/edit/{id}")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView editConfirm(@ModelAttribute("petEdit") PetEditBindingModel petEdit,
	                                @PathVariable String id,
	                                BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return view(EDIT_PET);
		}
		PetServiceModel petServiceModel = this.petService.getPetById(id);
		try {
			this.petService.updatePet(petServiceModel, petEdit);
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return redirect(HOME);
	}
	
	@GetMapping("/delete/{id}")
	@PageTitle("\uD835\uDD7B\uD835\uDD8A\uD835\uDD99 \uD835\uDD6F\uD835\uDD8A\uD835\uDD91\uD835\uDD8A\uD835\uDD99\uD835\uDD8A")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView delete(@PathVariable String id) {
		this.petService.deletePet(id);
		return redirect(HOME);
	}
}
