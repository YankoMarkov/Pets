package com.yanmark.pets.repositories;

import com.yanmark.pets.domain.entities.Pet;
import com.yanmark.pets.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, String> {
	
	List<Pet> getAllByOwnerOrderByBirthDate(User owner);
	
	Optional<Pet> getByName(String name);
}
