package com.yanmark.pets.repositories;

import com.yanmark.pets.domain.entities.Animal;
import com.yanmark.pets.domain.entities.Pet;
import com.yanmark.pets.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, String> {

  Page<Pet> getAllByOrderByBirthDateDesc(Pageable pageable);

  Page<Pet> getAllByAnimalOrderByBirthDateDesc(Animal animal, Pageable pageable);

  Page<Pet> getAllByOwnerOrderByBirthDateDesc(User owner, Pageable pageable);

  Page<Pet> getAllByOwnerAndAnimalOrderByBirthDateDesc(
      User owner, Animal animal, Pageable pageable);

  Optional<Pet> getByName(String name);
}
