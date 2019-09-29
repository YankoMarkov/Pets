package com.yanmark.pets.services;

import com.yanmark.pets.domain.entities.Illness;
import com.yanmark.pets.domain.entities.Pet;
import com.yanmark.pets.domain.models.bindings.illnesses.IllnessCreateBindingModel;
import com.yanmark.pets.domain.models.bindings.illnesses.IllnessEditBindingModel;
import com.yanmark.pets.domain.models.services.IllnessServiceModel;
import com.yanmark.pets.domain.models.services.ImageServiceModel;
import com.yanmark.pets.domain.models.services.PetServiceModel;
import com.yanmark.pets.repositories.IllnessRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class IllnessServiceImpl implements IllnessService {
	
	private static final String ILLNESS_NOT_FOUND = "Illness was not found!";
	
	private final IllnessRepository illnessRepository;
	private final ImageService imageService;
	private final PetService petService;
	private final ModelMapper modelMapper;
	private final CloudinaryService cloudinaryService;
	
	@Autowired
	public IllnessServiceImpl(IllnessRepository illnessRepository,
	                          ImageService imageService,
	                          PetService petService,
	                          ModelMapper modelMapper,
	                          CloudinaryService cloudinaryService) {
		this.illnessRepository = illnessRepository;
		this.imageService = imageService;
		this.petService = petService;
		this.modelMapper = modelMapper;
		this.cloudinaryService = cloudinaryService;
	}
	
	
	@Override
	public IllnessServiceModel saveIllness(IllnessCreateBindingModel illnessCreate, String petId) throws IOException {
		PetServiceModel petServiceModel = this.petService.getPetById(petId);
		IllnessServiceModel illnessServiceModel = this.modelMapper.map(illnessCreate, IllnessServiceModel.class);
		
		illnessServiceModel.setPet(petServiceModel);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(illnessCreate.getDate(), formatter);
		illnessServiceModel.setDate(date);
		
		Illness illness = this.modelMapper.map(illnessServiceModel, Illness.class);
		try {
			illness = this.illnessRepository.save(illness);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		if (!illnessCreate.getImage().isEmpty()) {
			IllnessServiceModel illnessService = this.modelMapper.map(illness, IllnessServiceModel.class);
			ImageServiceModel imageServiceModel = new ImageServiceModel();
			imageServiceModel.setImage(this.cloudinaryService.uploadImage(illnessCreate.getImage()));
			imageServiceModel.setIllness(illnessService);
			imageServiceModel = this.imageService.saveImage(imageServiceModel);
			illnessServiceModel.getImages().add(imageServiceModel);
		}
		illnessServiceModel = this.modelMapper.map(illness, IllnessServiceModel.class);
		petServiceModel.getIllnesses().add(illnessServiceModel);
		this.petService.addIllness(petServiceModel);
		return this.modelMapper.map(illness, IllnessServiceModel.class);
	}
	
	@Override
	public IllnessServiceModel updateIllness(IllnessServiceModel illnessService, IllnessEditBindingModel illnessEdit) throws IOException {
		DateTimeFormatter stringFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		String illnessDate = illnessService.getDate().format(stringFormatter);
		
		if (!illnessService.getName().equals(illnessEdit.getName())) {
			illnessService.setName(illnessEdit.getName());
		}
		if (!illnessService.getDescription().equals(illnessEdit.getDescription())) {
			illnessService.setDescription(illnessEdit.getDescription());
		}
		if (!illnessEdit.getDate().equals("") && !illnessDate.equals(illnessEdit.getDate())) {
			LocalDate date = LocalDate.parse(illnessEdit.getDate(), dateFormatter);
			illnessService.setDate(date);
		}
		if (!illnessEdit.getImage().isEmpty()) {
			ImageServiceModel imageServiceModel = new ImageServiceModel();
			imageServiceModel.setImage(this.cloudinaryService.uploadImage(illnessEdit.getImage()));
			imageServiceModel.setIllness(illnessService);
			imageServiceModel = this.imageService.saveImage(imageServiceModel);
			illnessService.getImages().add(imageServiceModel);
		}
		Illness illness = this.modelMapper.map(illnessService, Illness.class);
		try {
			illness = this.illnessRepository.save(illness);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return this.modelMapper.map(illness, IllnessServiceModel.class);
	}
	
	@Override
	public IllnessServiceModel getIllnessById(String id) {
		Illness illness = this.illnessRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(ILLNESS_NOT_FOUND));
		return this.modelMapper.map(illness, IllnessServiceModel.class);
	}
	
	@Override
	public IllnessServiceModel getIllnessByPet(PetServiceModel petService) {
		Pet pet = this.modelMapper.map(petService, Pet.class);
		Illness illness = this.illnessRepository.findByPet(pet)
				.orElseThrow(() -> new IllegalArgumentException(ILLNESS_NOT_FOUND));
		return this.modelMapper.map(illness, IllnessServiceModel.class);
	}
}
