package com.yanmark.pets.web.controllers;

import com.yanmark.pets.domain.models.bindings.pets.PetCreateBindingModel;
import com.yanmark.pets.domain.models.bindings.pets.PetEditBindingModel;
import com.yanmark.pets.domain.models.services.PetServiceModel;
import com.yanmark.pets.domain.models.views.pets.PetDetailsViewModel;
import com.yanmark.pets.domain.models.views.pets.PetEditViewModel;
import com.yanmark.pets.services.PetService;
import com.yanmark.pets.web.annotations.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/pets")
public class PetController extends BaseController {
	
	private static final String HOME = "/home";
	private static final String DETAILS_PET = "/pets/details/";
	private static final String EDIT_PET = "pets/edit-pet";
	private static final String CREATE_PET = "pets/create-pet";
	private static final String PET_DETAILS = "pets/pet-details";
	
	private final PetService petService;
	
	@Autowired
	public PetController(PetService petService) {
		this.petService = petService;
	}
	
	@GetMapping("/add")
	@PageTitle("\uD835\uDD7B\uD835\uDD8A\uD835\uDD99 \uD835\uDD6C\uD835\uDD89\uD835\uDD89")
	@PreAuthorize("isAuthenticated()")
	ModelAndView create(@ModelAttribute("petCreate") PetCreateBindingModel petCreate) {
		return this.view(CREATE_PET);
	}
	
	@PostMapping("/add")
	@PreAuthorize("isAuthenticated()")
	ModelAndView createConfirm(
			@Valid @ModelAttribute("petCreate") PetCreateBindingModel petCreate,
			BindingResult bindingResult,
			Principal principal)
			throws IOException {
		if (bindingResult.hasErrors()) {
			return view(CREATE_PET);
		}
		if (petCreate.getImage().isEmpty()) {
			return view(CREATE_PET);
		}
		this.petService.savePet(petCreate, principal);
		return this.redirect(HOME);
	}
	
	@GetMapping("/details/{id}/{page}")
	@PageTitle(
			"\uD835\uDD7B\uD835\uDD8A\uD835\uDD99 \uD835\uDD6F\uD835\uDD8A\uD835\uDD99\uD835\uDD86\uD835\uDD8E\uD835\uDD91\uD835\uDD98")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView details(
			@PathVariable String id, @PathVariable String page, ModelAndView modelAndView) {
		PetServiceModel petServiceModel = this.petService.getPetById(id);
		PetDetailsViewModel petDetailsViewModel = new PetDetailsViewModel(petServiceModel);
		
		modelAndView.addObject("vaccineDateExpired", this.petService.vaccineDateExpired(id));
		modelAndView.addObject("pet", petDetailsViewModel);
		modelAndView.addObject("page", page);
		return this.view(PET_DETAILS, modelAndView);
	}
	
	@GetMapping("/edit/{id}/{page}")
	@PageTitle(
			"\uD835\uDD7B\uD835\uDD8A\uD835\uDD99 \uD835\uDD70\uD835\uDD89\uD835\uDD8E\uD835\uDD99")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView edit(
			@PathVariable String id, @PathVariable String page, ModelAndView modelAndView) {
		PetServiceModel petServiceModel = this.petService.getPetById(id);
		PetEditViewModel petEditViewModel = new PetEditViewModel(petServiceModel);
		
		modelAndView.addObject("pet", petEditViewModel);
		modelAndView.addObject("page", page);
		return view(EDIT_PET, modelAndView);
	}
	
	@PostMapping("/edit/{id}/{page}")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView editConfirm(
			@Valid @ModelAttribute("petEdit") PetEditBindingModel petEdit,
			@PathVariable String id,
			@PathVariable String page,
			BindingResult bindingResult)
			throws IOException {
		if (bindingResult.hasErrors()) {
			return view(EDIT_PET);
		}
		PetServiceModel petServiceModel = this.petService.getPetById(id);
		this.petService.updatePet(petServiceModel, petEdit);
		return redirect(DETAILS_PET + id + "/" + page);
	}
	
	@GetMapping("/delete/{id}")
	@PageTitle(
			"\uD835\uDD7B\uD835\uDD8A\uD835\uDD99 \uD835\uDD6F\uD835\uDD8A\uD835\uDD91\uD835\uDD8A\uD835\uDD99\uD835\uDD8A")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView delete(@PathVariable String id) {
		this.petService.deletePet(id);
		return redirect(HOME);
	}
}
