package com.yanmark.pets.web.controllers;

import com.yanmark.pets.domain.models.bindings.animals.AnimalCreateBindingModel;
import com.yanmark.pets.domain.models.services.AnimalServiceModel;
import com.yanmark.pets.domain.models.views.animals.AnimalViewModel;
import com.yanmark.pets.services.AnimalService;
import com.yanmark.pets.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/animals")
public class AnimalController extends BaseController {
	
	private static final String ALL_ANIMALS = "animals/all-animals";
	private static final String CREATE_ANIMAL = "animals/create-animal";
	private static final String HOME = "/home";
	private static final String EDIT_ANIMAL = "animals/edit-animal";
	
	private final AnimalService animalService;
	private final ModelMapper modelMapper;
	
	@Autowired
	public AnimalController(AnimalService animalService,
	                        ModelMapper modelMapper) {
		this.animalService = animalService;
		this.modelMapper = modelMapper;
	}
	
	@GetMapping("/all")
	@PageTitle("\uD835\uDD6C\uD835\uDD93\uD835\uDD8E\uD835\uDD92\uD835\uDD86\uD835\uDD91 \uD835\uDD6C\uD835\uDD91\uD835\uDD91")
	@PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
	ModelAndView all(ModelAndView modelAndView) {
		List<AnimalViewModel> animalViewModels = this.animalService.getAllAnimals().stream()
				.map(animal -> this.modelMapper.map(animal, AnimalViewModel.class))
				.collect(Collectors.toList());
		modelAndView.addObject("animals", animalViewModels);
		return this.view(ALL_ANIMALS, modelAndView);
	}
	
	@GetMapping("/add")
	@PageTitle("\uD835\uDD6C\uD835\uDD93\uD835\uDD8E\uD835\uDD92\uD835\uDD86\uD835\uDD91 \uD835\uDD6C\uD835\uDD89\uD835\uDD89")
	@PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
	ModelAndView create(@ModelAttribute("animalCreate") AnimalCreateBindingModel animalCreate) {
		return this.view(CREATE_ANIMAL);
	}
	
	@PostMapping("/add")
	@PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
	ModelAndView createConfirm(@Valid @ModelAttribute("animalCreate") AnimalCreateBindingModel animalCreate,
	                           BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return this.view(CREATE_ANIMAL);
		}
		AnimalServiceModel animalServiceModel = this.modelMapper.map(animalCreate, AnimalServiceModel.class);
		this.animalService.saveAnimal(animalServiceModel);
		return redirect(HOME);
	}
	
	@GetMapping("/edit/{id}")
	@PageTitle("\uD835\uDD6C\uD835\uDD93\uD835\uDD8E\uD835\uDD92\uD835\uDD86\uD835\uDD91 \uD835\uDD70\uD835\uDD89\uD835\uDD8E\uD835\uDD99")
	@PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
	ModelAndView edit(@ModelAttribute("animalCreate") AnimalCreateBindingModel animalCreate,
	                  @PathVariable String id,
	                  ModelAndView modelAndView) {
		AnimalServiceModel animalServiceModel = this.animalService.getAnimalById(id);
		AnimalViewModel animalViewModel = this.modelMapper.map(animalServiceModel, AnimalViewModel.class);
		modelAndView.addObject("animal", animalViewModel);
		return this.view(EDIT_ANIMAL, modelAndView);
	}
	
	@PostMapping("/edit/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
	ModelAndView editConfirm(@Valid @ModelAttribute("animalCreate") AnimalCreateBindingModel animalCreate,
	                         @PathVariable String id,
	                         BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return this.view(EDIT_ANIMAL);
		}
		this.animalService.editAnimal(animalCreate, id);
		return this.redirect(HOME);
	}
	
	@PostMapping("/delete/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
	ModelAndView deleteConfirm(@PathVariable String id) {
		this.animalService.deleteAnimal(id);
		return redirect(HOME);
	}
	
	@GetMapping("/fetch")
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	List<AnimalViewModel> fetch() {
		return this.animalService.getAllAnimals().stream()
				.map(animal -> this.modelMapper.map(animal, AnimalViewModel.class))
				.collect(Collectors.toList());
	}
}
