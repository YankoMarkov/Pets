package com.yanmark.pets.services;

import com.cloudinary.Cloudinary;
import com.yanmark.pets.repositories.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RunWith(MockitoJUnitRunner.Silent.class)
public class IllnessServiceImplTest {

    @Mock
    private IllnessRepository mockIllnessRepository;
    @Mock
    private ImageRepository mockImageRepository;
    @Mock
    private PetRepository mockPetRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private AnimalRepository mockAnimalRepository;
    @Mock
    private UserRoleRepository mockUserRoleRepository;
    private IllnessServiceImpl illnessService;
    private ImageServiceImpl imageService;
    private PetServiceImpl petService;
    private UserServiceImpl userService;
    private AnimalServiceImpl animalService;
    private CloudinaryServiceImpl cloudinaryService;
    private UserRoleServiceImpl userRoleService;

    @Before
    public void setUp() {
        this.cloudinaryService = new CloudinaryServiceImpl(new Cloudinary());
        this.userRoleService = new UserRoleServiceImpl(mockUserRoleRepository,
                new ModelMapper());
        this.animalService = new AnimalServiceImpl(mockAnimalRepository,
                new ModelMapper(),
                new AnimalValidationServiceImpl());
        this.userService = new UserServiceImpl(mockUserRepository,
                userRoleService,
                new UserValidationServiceImpl(),
                new ModelMapper(),
                new BCryptPasswordEncoder());
        this.petService = new PetServiceImpl(new ModelMapper(),
                mockPetRepository,
                animalService,
                userService,
                cloudinaryService,
                new PetValidationServiceImpl());
        this.imageService = new ImageServiceImpl(new ModelMapper(),
                mockImageRepository);
        this.illnessService = new IllnessServiceImpl(mockIllnessRepository,
                imageService,
                petService,
                new ModelMapper(),
                cloudinaryService,
                new IllnessValidationServiceImpl());
    }

    @Test
    public void saveIllness() {
    }

    @Test
    public void updateIllness() {
    }

    @Test
    public void getIllnessById() {

    }

    @Test
    public void getIllnessesByPet() {
    }
}