package com.yanmark.pets.repositories;

import com.yanmark.pets.domain.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {

    Optional<UserRole> findByAuthority(String authority);
}
