package com.yanmark.pets.repositories;

import com.yanmark.pets.domain.entities.Illness;
import com.yanmark.pets.domain.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IllnessRepository extends JpaRepository<Illness, String> {

  Optional<Illness> findByPet(Pet pet);
}
