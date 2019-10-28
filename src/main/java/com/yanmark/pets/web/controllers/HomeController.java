package com.yanmark.pets.web.controllers;

import com.yanmark.pets.domain.models.services.PetServiceModel;
import com.yanmark.pets.domain.models.views.animals.AnimalViewModel;
import com.yanmark.pets.domain.models.views.pets.AllPetsViewModel;
import com.yanmark.pets.domain.models.views.pets.PetHomeViewModel;
import com.yanmark.pets.domain.models.views.users.UserViewModel;
import com.yanmark.pets.services.AnimalService;
import com.yanmark.pets.services.HomeService;
import com.yanmark.pets.services.UserService;
import com.yanmark.pets.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {
	
	private static final String HOMES = "/home";
	private static final String HOME = "home";
	private static final String ALL_PETS = "pets/all-pets";
	private static final String INDEX = "index";
	
	private final HomeService homeService;
	private final AnimalService animalService;
	private final UserService userService;
	private final ModelMapper modelMapper;
	
	@Autowired
	public HomeController(HomeService homeService,
	                      AnimalService animalService,
	                      UserService userService,
	                      ModelMapper modelMapper) {
		this.homeService = homeService;
		this.animalService = animalService;
		this.userService = userService;
		this.modelMapper = modelMapper;
	}
	
	@GetMapping("/")
	@PageTitle("\uD835\uDD7B\uD835\uDD8A\uD835\uDD99 \uD835\uDD6F\uD835\uDD8E\uD835\uDD86\uD835\uDD97\uD835\uDD9E")
	ModelAndView index(Principal principal) {
		if (principal != null) {
			return this.redirect(HOMES);
		}
		return this.view(INDEX);
	}
	
	@GetMapping("/home")
	@PreAuthorize("isAuthenticated()")
	@PageTitle("\uD835\uDD7B\uD835\uDD8A\uD835\uDD99 \uD835\uDD6F\uD835\uDD8E\uD835\uDD86\uD835\uDD97\uD835\uDD9E")
	ModelAndView home(ModelAndView modelAndView,
	                  Principal principal,
	                  HttpServletRequest request,
	                  @RequestParam(required = false) String animalId) {
		List<AnimalViewModel> animalViewModels = this.animalService.getAllAnimals().stream()
				.map(animal -> this.modelMapper.map(animal, AnimalViewModel.class))
				.collect(Collectors.toList());
		Page<PetServiceModel> petServiceModels = this.homeService.takePetsByUser(animalId, modelAndView, principal, request);
		Page<PetHomeViewModel> petHomeViewModels = petServiceModels.map(PetHomeViewModel::new);
		modelAndView.addObject("animals", animalViewModels);
		modelAndView.addObject("pets", petHomeViewModels);
		return this.view(HOME, modelAndView);
	}
	
	@GetMapping("/allPets")
	@PreAuthorize("hasAuthority('ROOT')")
	@PageTitle("\uD835\uDD6C\uD835\uDD91\uD835\uDD91 \uD835\uDD7B\uD835\uDD8A\uD835\uDD99\uD835\uDD98")
	ModelAndView allPets(ModelAndView modelAndView,
	                     HttpServletRequest request,
	                     @RequestParam(required = false) String userId) {
		List<UserViewModel> userViewModels = this.userService.getAllUsers().stream()
				.map(user -> this.modelMapper.map(user, UserViewModel.class))
				.collect(Collectors.toList());
		Page<PetServiceModel> petServiceModels = this.homeService.takeAllPets(userId, request);
		Page<AllPetsViewModel> allPetsViewModels = petServiceModels.map(AllPetsViewModel::new);
		modelAndView.addObject("users", userViewModels);
		modelAndView.addObject("pets", allPetsViewModels);
		return this.view(ALL_PETS, modelAndView);
	}
}
