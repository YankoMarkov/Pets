package com.yanmark.pets.web.controllers;

import com.yanmark.pets.domain.models.bindings.illnesses.IllnessCreateBindingModel;
import com.yanmark.pets.domain.models.bindings.illnesses.IllnessEditBindingModel;
import com.yanmark.pets.domain.models.services.IllnessServiceModel;
import com.yanmark.pets.domain.models.services.PetServiceModel;
import com.yanmark.pets.domain.models.views.illnesses.IllnessDetailsViewModel;
import com.yanmark.pets.domain.models.views.illnesses.IllnessEditViewModel;
import com.yanmark.pets.domain.models.views.illnesses.IllnessViewModel;
import com.yanmark.pets.services.IllnessService;
import com.yanmark.pets.services.PetService;
import com.yanmark.pets.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/illnesses")
public class IllnessController extends BaseController {
	
	private static final String HEALTH = "illnesses/health";
	private static final String CREATE_ILLNESS = "illnesses/create-illness";
	private static final String ALL = "/illnesses/all/";
	private static final String ILLNESS_DETAILS = "illnesses/illness-details";
	private static final String EDIT_ILLNESS = "illnesses/edit-illness";
	
	private final IllnessService illnessService;
	private final PetService petService;
	private final ModelMapper modelMapper;
	
	@Autowired
	public IllnessController(IllnessService illnessService,
	                         PetService petService,
	                         ModelMapper modelMapper) {
		this.illnessService = illnessService;
		this.petService = petService;
		this.modelMapper = modelMapper;
	}
	
	@GetMapping("/all/{id}")
	@PageTitle("\uD835\uDD74\uD835\uDD91\uD835\uDD91\uD835\uDD93\uD835\uDD8A\uD835\uDD98\uD835\uDD98 \uD835\uDD6C\uD835\uDD91\uD835\uDD91")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView all(@PathVariable String id,
	                        ModelAndView modelAndView) {
		PetServiceModel petServiceModel = this.petService.getPetById(id);
		List<IllnessViewModel> illnessViewModels = new ArrayList<>();
		if (!petServiceModel.getIllnesses().isEmpty()) {
			illnessViewModels = petServiceModel.getIllnesses().stream()
					.sorted((a, b) -> b.getDate().compareTo(a.getDate()))
					.map(IllnessViewModel::new)
					.collect(Collectors.toList());
		}
		modelAndView.addObject("illnesses", illnessViewModels);
		modelAndView.addObject("petId", id);
		return view(HEALTH, modelAndView);
	}
	
	@GetMapping("/add/{id}")
	@PageTitle("\uD835\uDD74\uD835\uDD91\uD835\uDD91\uD835\uDD93\uD835\uDD8A\uD835\uDD98\uD835\uDD98 \uD835\uDD6C\uD835\uDD89\uD835\uDD89")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView add(@PathVariable String id,
	                        @ModelAttribute("illnessCreate") IllnessCreateBindingModel illnessCreate,
	                        ModelAndView modelAndView) {
		modelAndView.addObject("petId", id);
		return this.view(CREATE_ILLNESS, modelAndView);
	}
	
	@PostMapping("/add/{id}")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView addConfirm(@PathVariable String id,
	                               @ModelAttribute("illnessCreate") IllnessCreateBindingModel illnessCreate,
	                               BindingResult bindingResult,
	                               ModelAndView modelAndView) {
		modelAndView.addObject("petId", id);
		if (bindingResult.hasErrors()) {
			return this.view(CREATE_ILLNESS, modelAndView);
		}
		try {
			this.illnessService.saveIllness(illnessCreate, id);
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return this.redirect(ALL + id);
	}
	
	@GetMapping("/details/{id}")
	@PageTitle("\uD835\uDD74\uD835\uDD91\uD835\uDD91\uD835\uDD93\uD835\uDD8A\uD835\uDD98\uD835\uDD98 \uD835\uDD6F\uD835\uDD8A\uD835\uDD99\uD835\uDD86\uD835\uDD8E\uD835\uDD91\uD835\uDD98")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView details(@PathVariable String id,
	                            @RequestParam("petId") String petId,
	                            ModelAndView modelAndView) {
		IllnessServiceModel illnessServiceModel = this.illnessService.getIllnessById(id);
		IllnessDetailsViewModel illnessDetailsViewModel = new IllnessDetailsViewModel(illnessServiceModel);
		
		modelAndView.addObject("illness", illnessDetailsViewModel);
		modelAndView.addObject("petId", petId);
		return this.view(ILLNESS_DETAILS, modelAndView);
	}
	
	@GetMapping("/edit/{id}")
	@PageTitle("\uD835\uDD74\uD835\uDD91\uD835\uDD91\uD835\uDD93\uD835\uDD8A\uD835\uDD98\uD835\uDD98 \uD835\uDD70\uD835\uDD89\uD835\uDD8E\uD835\uDD99")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView edit(@PathVariable String id,
	                         @RequestParam("petId") String petId,
	                         ModelAndView modelAndView) {
		IllnessServiceModel illnessServiceModel = this.illnessService.getIllnessById(id);
		IllnessEditViewModel illnessEditViewModel = this.modelMapper.map(illnessServiceModel, IllnessEditViewModel.class);
		
		modelAndView.addObject("illness", illnessEditViewModel);
		modelAndView.addObject("petId", petId);
		return this.view(EDIT_ILLNESS, modelAndView);
	}
	
	@PostMapping("/edit/{id}")
	@PreAuthorize("isAuthenticated()")
	public ModelAndView editConfirm(@PathVariable String id,
	                                @ModelAttribute("illnessEdit") IllnessEditBindingModel illnessEdit,
	                                BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return view(EDIT_ILLNESS);
		}
		IllnessServiceModel illnessServiceModel = this.illnessService.getIllnessById(id);
		try {
			this.illnessService.updateIllness(illnessServiceModel, illnessEdit);
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return this.redirect(ALL + illnessServiceModel.getPet().getId());
	}
}
