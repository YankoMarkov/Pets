package com.yanmark.pets.services;

import com.yanmark.pets.domain.entities.Animal;
import com.yanmark.pets.domain.models.services.AnimalServiceModel;
import com.yanmark.pets.repositories.AnimalRepository;
import com.yanmark.pets.testUtils.AnimalUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AnimalServiceImplTest {

    @Mock
    private AnimalRepository mockAnimalRepository;
    private AnimalServiceImpl animalService;

    @Before
    public void setUp() {
        this.animalService = new AnimalServiceImpl(mockAnimalRepository,
                new ModelMapper(),
                new AnimalValidationServiceImpl());
    }

    @Test
    public void saveAnimal_whenValidAnimal_thanAnimal() {
        Animal animal = AnimalUtils.getAnimal();
        when(mockAnimalRepository.save(any()))
                .thenReturn(animal);

        AnimalServiceModel animalService = this.animalService.saveAnimal(AnimalUtils.getAnimalBinding());

        Assert.assertEquals(animal.getName(), animalService.getName());
    }

    @Test(expected = Exception.class)
    public void saveAnimal_whenAnimalNameExist_thanError() {
        Animal animal = AnimalUtils.getAnimal();
        when(mockAnimalRepository.save(any()))
                .thenReturn(animal);
        when(mockAnimalRepository.findByName("name"))
                .thenReturn(java.util.Optional.of(animal));

        this.animalService.saveAnimal(AnimalUtils.getAnimalBinding());
    }

    @Test
    public void editAnimal_whenValidAnimal_thenAnimal() {
        Animal animal = AnimalUtils.getAnimal();
        when(mockAnimalRepository.save(any()))
                .thenReturn(animal);
        when(mockAnimalRepository.findById("id"))
                .thenReturn(java.util.Optional.of(animal));

        AnimalServiceModel animalService = this.animalService.editAnimal(AnimalUtils.getAnimalBinding(), "id");

        Assert.assertEquals(animal.getName(), animalService.getName());
    }

    @Test(expected = Exception.class)
    public void editAnimal_whenAnimalNameExist_thenError() {
        Animal animal = AnimalUtils.getAnimal();
        when(mockAnimalRepository.save(any()))
                .thenReturn(animal);
        when(mockAnimalRepository.findById("id"))
                .thenReturn(java.util.Optional.of(animal));
        when(mockAnimalRepository.findByName("name"))
                .thenReturn(java.util.Optional.of(animal));

        this.animalService.editAnimal(AnimalUtils.getAnimalBinding(), "id");
    }

    @Test(expected = Exception.class)
    public void editAnimal_whenAnimalIdIsWrong_thenError() {
        Animal animal = AnimalUtils.getAnimal();
        when(mockAnimalRepository.save(any()))
                .thenReturn(animal);
        when(mockAnimalRepository.findById("id"))
                .thenReturn(java.util.Optional.of(animal));

        this.animalService.editAnimal(AnimalUtils.getAnimalBinding(), "id1");
    }

    @Test
    public void getAnimalById_whenValidId_thenAnimal() {
        Animal animal = AnimalUtils.getAnimal();
        when(mockAnimalRepository.findById("id"))
                .thenReturn(java.util.Optional.of(animal));

        AnimalServiceModel animalService = this.animalService.getAnimalById("id");

        Assert.assertEquals(animal.getId(), animalService.getId());
    }

    @Test(expected = Exception.class)
    public void getAnimalById_whenWrongId_thenError() {
        Animal animal = AnimalUtils.getAnimal();
        when(mockAnimalRepository.findById("id"))
                .thenReturn(java.util.Optional.of(animal));

        this.animalService.getAnimalById("23");
    }

    @Test(expected = Exception.class)
    public void getAnimalById_whenIdIsNull_thenError() {
        Animal animal = AnimalUtils.getAnimal();
        when(mockAnimalRepository.findById("id"))
                .thenReturn(java.util.Optional.of(animal));

        this.animalService.getAnimalById(null);
    }

    @Test(expected = Exception.class)
    public void getAnimalById_whenIdIsEmpty_thenError() {
        Animal animal = AnimalUtils.getAnimal();
        when(mockAnimalRepository.findById("id"))
                .thenReturn(java.util.Optional.of(animal));

        this.animalService.getAnimalById("");
    }

    @Test
    public void getAnimalByName_whenValidName_thenAnimal() {
        Animal animal = AnimalUtils.getAnimal();
        when(mockAnimalRepository.findByName("name"))
                .thenReturn(java.util.Optional.of(animal));

        AnimalServiceModel animalService = this.animalService.getAnimalByName("name");

        Assert.assertEquals(animal.getName(), animalService.getName());
    }

    @Test(expected = Exception.class)
    public void getAnimalByName_whenWrongName_thenError() {
        Animal animal = AnimalUtils.getAnimal();
        when(mockAnimalRepository.findByName("name"))
                .thenReturn(java.util.Optional.of(animal));

        this.animalService.getAnimalByName("error");
    }

    @Test(expected = Exception.class)
    public void getAnimalByName_whenNameIsNull_thenError() {
        Animal animal = AnimalUtils.getAnimal();
        when(mockAnimalRepository.findByName("name"))
                .thenReturn(java.util.Optional.of(animal));

        this.animalService.getAnimalByName(null);
    }

    @Test(expected = Exception.class)
    public void getAnimalByName_whenNameIsEmpty_thenError() {
        Animal animal = AnimalUtils.getAnimal();
        when(mockAnimalRepository.findByName("name"))
                .thenReturn(java.util.Optional.of(animal));

        this.animalService.getAnimalByName("");
    }

    @Test
    public void getAllAnimals_when2Animal_then2Animal() {
        when(mockAnimalRepository.findAllAnimals())
                .thenReturn(AnimalUtils.getAnimals(2));

        List<AnimalServiceModel> serviceModel = this.animalService.getAllAnimals();

        Assert.assertEquals(2, serviceModel.size());
    }

    @Test
    public void getAllAnimals_wheNoAnimal_thenEmptyList() {
        List<AnimalServiceModel> serviceModel = this.animalService.getAllAnimals();

        Assert.assertEquals(0, serviceModel.size());
    }
}