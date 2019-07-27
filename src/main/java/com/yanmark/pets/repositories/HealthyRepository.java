package com.yanmark.pets.repositories;

import com.yanmark.pets.domain.entities.Health;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthyRepository extends JpaRepository<Health, String> {
}
