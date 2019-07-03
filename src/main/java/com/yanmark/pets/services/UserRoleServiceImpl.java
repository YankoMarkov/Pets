package com.yanmark.pets.services;

import com.yanmark.pets.domain.entities.UserRole;
import com.yanmark.pets.domain.models.services.UserRoleServiceModel;
import com.yanmark.pets.repositories.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, ModelMapper modelMapper) {
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserRoleServiceModel getRoleByName(String name) {
        UserRole role = this.userRoleRepository.findByAuthority(name)
                .orElseThrow(() -> new IllegalArgumentException("Role was not found!"));
        return this.modelMapper.map(role, UserRoleServiceModel.class);
    }

    @Override
    public Set<UserRoleServiceModel> getAllRoles() {
        return this.userRoleRepository.findAll().stream()
                .map(role -> this.modelMapper.map(role, UserRoleServiceModel.class))
                .collect(Collectors.toUnmodifiableSet());
    }
}
