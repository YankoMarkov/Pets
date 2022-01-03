package com.yanmark.pets.repositories;

import com.yanmark.pets.domain.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, String> {

  @Query("SELECT a FROM animals a ORDER BY a.name")
  List<Animal> findAllAnimals();

  Optional<Animal> findByName(String name);
}
