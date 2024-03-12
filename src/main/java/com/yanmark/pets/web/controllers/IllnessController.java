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

import javax.validation.Valid;
import java.io.IOException;
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
  public IllnessController(
      IllnessService illnessService, PetService petService, ModelMapper modelMapper) {
    this.illnessService = illnessService;
    this.petService = petService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/all/{id}/{page}")
  @PageTitle(
      "\uD835\uDD74\uD835\uDD91\uD835\uDD91\uD835\uDD93\uD835\uDD8A\uD835\uDD98\uD835\uDD98 \uD835\uDD6C\uD835\uDD91\uD835\uDD91")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView all(
      @PathVariable String id, @PathVariable String page, ModelAndView modelAndView) {
    PetServiceModel petServiceModel = this.petService.getPetById(id);
    List<IllnessViewModel> illnessViewModels =
        this.illnessService.getIllnessesByPet(petServiceModel).stream()
            .map(illnessService -> this.modelMapper.map(illnessService, IllnessViewModel.class))
            .collect(Collectors.toUnmodifiableList());
    modelAndView.addObject("illnesses", illnessViewModels);
    modelAndView.addObject("petId", id);
    modelAndView.addObject("page", page);
    return view(HEALTH, modelAndView);
  }

  @GetMapping("/add/{id}/{page}")
  @PageTitle(
      "\uD835\uDD74\uD835\uDD91\uD835\uDD91\uD835\uDD93\uD835\uDD8A\uD835\uDD98\uD835\uDD98 \uD835\uDD6C\uD835\uDD89\uD835\uDD89")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView add(
      @PathVariable String id,
      @PathVariable String page,
      @ModelAttribute("illnessCreate") IllnessCreateBindingModel illnessCreate,
      ModelAndView modelAndView) {
    modelAndView.addObject("petId", id);
    modelAndView.addObject("page", page);
    return this.view(CREATE_ILLNESS, modelAndView);
  }

  @PostMapping("/add/{id}/{page}")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView addConfirm(
      @PathVariable String id,
      @PathVariable String page,
      @Valid @ModelAttribute("illnessCreate") IllnessCreateBindingModel illnessCreate,
      BindingResult bindingResult,
      ModelAndView modelAndView)
      throws IOException {
    modelAndView.addObject("petId", id);
    if (bindingResult.hasErrors()) {
      return this.view(CREATE_ILLNESS, modelAndView);
    }
    this.illnessService.saveIllness(illnessCreate, id);
    return this.redirect(ALL + id + "/" + page);
  }

  @GetMapping("/details/{id}/{page}")
  @PageTitle(
      "\uD835\uDD74\uD835\uDD91\uD835\uDD91\uD835\uDD93\uD835\uDD8A\uD835\uDD98\uD835\uDD98 \uD835\uDD6F\uD835\uDD8A\uD835\uDD99\uD835\uDD86\uD835\uDD8E\uD835\uDD91\uD835\uDD98")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView details(
      @PathVariable String id,
      @PathVariable String page,
      @RequestParam("petId") String petId,
      ModelAndView modelAndView) {
    IllnessServiceModel illnessServiceModel = this.illnessService.getIllnessById(id);
    IllnessDetailsViewModel illnessDetailsViewModel =
        new IllnessDetailsViewModel(illnessServiceModel);

    modelAndView.addObject("illness", illnessDetailsViewModel);
    modelAndView.addObject("petId", petId);
    modelAndView.addObject("page", page);
    return this.view(ILLNESS_DETAILS, modelAndView);
  }

  @GetMapping("/edit/{id}/{page}")
  @PageTitle(
      "\uD835\uDD74\uD835\uDD91\uD835\uDD91\uD835\uDD93\uD835\uDD8A\uD835\uDD98\uD835\uDD98 \uD835\uDD70\uD835\uDD89\uD835\uDD8E\uD835\uDD99")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView edit(
      @PathVariable String id,
      @PathVariable String page,
      @RequestParam("petId") String petId,
      ModelAndView modelAndView) {
    IllnessServiceModel illnessServiceModel = this.illnessService.getIllnessById(id);
    IllnessEditViewModel illnessEditViewModel =
        this.modelMapper.map(illnessServiceModel, IllnessEditViewModel.class);

    modelAndView.addObject("illness", illnessEditViewModel);
    modelAndView.addObject("petId", petId);
    modelAndView.addObject("page", page);
    return this.view(EDIT_ILLNESS, modelAndView);
  }

  @PostMapping("/edit/{id}/{page}")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView editConfirm(
      @PathVariable String id,
      @PathVariable String page,
      @Valid @ModelAttribute("illnessEdit") IllnessEditBindingModel illnessEdit,
      BindingResult bindingResult)
      throws IOException {
    if (bindingResult.hasErrors()) {
      return view(EDIT_ILLNESS);
    }
    IllnessServiceModel illnessServiceModel = this.illnessService.getIllnessById(id);
    this.illnessService.updateIllness(illnessServiceModel, illnessEdit);
    return this.redirect(ALL + illnessServiceModel.getPet().getId() + "/" + page);
  }

  @GetMapping("/delete/{id}/{page}")
  @PageTitle(
      "\uD835\uDD7B\uD835\uDD8A\uD835\uDD99 \uD835\uDD6F\uD835\uDD8A\uD835\uDD91\uD835\uDD8A\uD835\uDD99\uD835\uDD8A")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView delete(
      @PathVariable String id, @PathVariable String page, @RequestParam("petId") String petId) {
    this.illnessService.deleteIllness(id);
    return redirect(ALL + petId + "/" + page);
  }
}
