package com.yanmark.pets.testUtils;

import com.yanmark.pets.domain.entities.Animal;
import com.yanmark.pets.domain.models.bindings.animals.AnimalCreateBindingModel;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AnimalUtils {

    public static List<Animal> getAnimals(int count) {
        return IntStream.range(0, count)
                .mapToObj(index -> new Animal() {{
                    setId("id" + index);
                    setName("name" + index);
                    setPets(new HashSet<>());
                }})
                .collect(Collectors.toList());
    }

    public static Animal getAnimal() {
        Animal animal = new Animal();
        animal.setId("id");
        animal.setName("name");
        animal.setPets(new HashSet<>());
        return animal;
    }

    public static AnimalCreateBindingModel getAnimalBinding() {
        AnimalCreateBindingModel animalCreateModel = new AnimalCreateBindingModel();
        animalCreateModel.setName("name");
        return animalCreateModel;
    }
}
