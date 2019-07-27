package com.yanmark.pets.web.controllers;

import com.yanmark.pets.domain.models.bindings.animals.AnimalCreateBindingModel;
import com.yanmark.pets.domain.models.services.AnimalServiceModel;
import com.yanmark.pets.domain.models.views.animals.AnimalViewModel;
import com.yanmark.pets.services.AnimalService;
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
	
	private final AnimalService animalService;
	private final ModelMapper modelMapper;
	
	@Autowired
	public AnimalController(AnimalService animalService,
	                        ModelMapper modelMapper) {
		this.animalService = animalService;
		this.modelMapper = modelMapper;
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
	ModelAndView all(ModelAndView modelAndView) {
		List<AnimalViewModel> animalViewModels = this.animalService.getAllAnimals().stream()
				.map(animal -> this.modelMapper.map(animal, AnimalViewModel.class))
				.collect(Collectors.toList());
		modelAndView.addObject("animals", animalViewModels);
		return this.view("/animals/all-animals", modelAndView);
	}
	
	@GetMapping("/add")
	@PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
	ModelAndView create(@ModelAttribute("animalCreate") AnimalCreateBindingModel animalCreate) {
		return this.view("/animals/create-animal");
	}
	
	@PostMapping("/add")
	@PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
	ModelAndView createConfirm(@Valid @ModelAttribute("animalCreate") AnimalCreateBindingModel animalCreate,
	                           BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return this.view("/animals/create-animal");
		}
		AnimalServiceModel animalServiceModel = this.modelMapper.map(animalCreate, AnimalServiceModel.class);
		this.animalService.saveAnimal(animalServiceModel);
		return redirect("/home");
	}
	
	@GetMapping("/edit/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
	ModelAndView edit(@ModelAttribute("animalCreate") AnimalCreateBindingModel animalCreate,
	                  @PathVariable String id,
	                  ModelAndView modelAndView) {
		AnimalServiceModel animalServiceModel = this.animalService.getAnimalById(id);
		AnimalViewModel animalViewModel = this.modelMapper.map(animalServiceModel, AnimalViewModel.class);
		modelAndView.addObject("animal", animalViewModel);
		return this.view("/animals/edit-animal", modelAndView);
	}
	
	@PostMapping("/edit/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
	ModelAndView editConfirm(@Valid @ModelAttribute("animalCreate") AnimalCreateBindingModel animalCreate,
	                         @PathVariable String id,
	                         BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return this.view("/animals/edit-animal");
		}
		this.animalService.editAnimal(animalCreate, id);
		return this.redirect("/home");
	}
	
	@PostMapping("/delete/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
	ModelAndView deleteConfirm(@PathVariable String id) {
		this.animalService.deleteAnimal(id);
		return redirect("/home");
	}
	
	@GetMapping("/fetch")
	@PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
	@ResponseBody
	List<AnimalViewModel> fetch() {
		return this.animalService.getAllAnimals().stream()
				.map(animal -> this.modelMapper.map(animal, AnimalViewModel.class))
				.collect(Collectors.toList());
	}
}
