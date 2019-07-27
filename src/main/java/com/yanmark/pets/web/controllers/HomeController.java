package com.yanmark.pets.web.controllers;

import com.yanmark.pets.domain.models.views.animals.AnimalViewModel;
import com.yanmark.pets.domain.models.views.pets.PetHomeViewModel;
import com.yanmark.pets.services.AnimalService;
import com.yanmark.pets.services.HomeService;
import com.yanmark.pets.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {
	
	private final HomeService homeService;
	private final AnimalService animalService;
	private final ModelMapper modelMapper;
	
	@Autowired
	public HomeController(HomeService homeService,
	                      AnimalService animalService,
	                      ModelMapper modelMapper) {
		this.homeService = homeService;
		this.animalService = animalService;
		this.modelMapper = modelMapper;
	}
	
	@GetMapping("/")
	@PageTitle("\uD835\uDC3C\uD835\uDCC3\uD835\uDCB9\uD835\uDC52\uD835\uDCCD")
	ModelAndView index(Principal principal) {
		if (principal != null) {
			return this.redirect("/home");
		}
		return this.view("index");
	}
	
	@GetMapping("/home")
	@PreAuthorize("isAuthenticated()")
	@PageTitle("\uD835\uDC3C\uD835\uDCC3\uD835\uDCB9\uD835\uDC52\uD835\uDCCD")
	ModelAndView home(ModelAndView modelAndView,
	                  Principal principal,
	                  @RequestParam(required = false) String animalId) {
		List<AnimalViewModel> animalViewModels = this.animalService.getAllAnimals().stream()
				.map(animal -> this.modelMapper.map(animal, AnimalViewModel.class))
				.collect(Collectors.toList());
		List<PetHomeViewModel> petHomeViewModels =
				this.homeService.takePets(animalId, modelAndView, principal).stream()
						.map(pet -> {
							PetHomeViewModel petHomeViewModel = this.modelMapper.map(pet, PetHomeViewModel.class);
							long age = ChronoUnit.MONTHS.between(
									pet.getBirthDate().withDayOfMonth(1),
									LocalDate.now().withDayOfMonth(1));
							petHomeViewModel.setAgeInYears((int) age / 12);
							petHomeViewModel.setAgeInMonths((int) age % 12);
							petHomeViewModel.setAnimal(pet.getAnimal().getName());
							petHomeViewModel.setGender(pet.getGender().getGender());
							return petHomeViewModel;
						})
						.collect(Collectors.toList());
		modelAndView.addObject("animals", animalViewModels);
		modelAndView.addObject("pets", petHomeViewModels);
		return this.view("home", modelAndView);
	}
}
