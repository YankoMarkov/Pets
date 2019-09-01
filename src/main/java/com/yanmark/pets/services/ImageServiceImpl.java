package com.yanmark.pets.services;

import com.yanmark.pets.domain.entities.Image;
import com.yanmark.pets.domain.models.services.ImageServiceModel;
import com.yanmark.pets.repositories.ImageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
	
	private final ModelMapper modelMapper;
	private final ImageRepository imageRepository;
	
	@Autowired
	public ImageServiceImpl(ModelMapper modelMapper,
	                        ImageRepository imageRepository) {
		this.modelMapper = modelMapper;
		this.imageRepository = imageRepository;
	}
	
	@Override
	public ImageServiceModel saveImage(ImageServiceModel imageService) {
		Image image = this.modelMapper.map(imageService, Image.class);
		try {
			image = this.imageRepository.save(image);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return this.modelMapper.map(image, ImageServiceModel.class);
	}
}
