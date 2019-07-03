package com.yanmark.pets.repositories;

import com.yanmark.pets.domain.entities.Illness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IllnessRepository extends JpaRepository<Illness, String> {
}
