package com.yanmark.pets.services;

import com.yanmark.pets.domain.models.services.UserRoleServiceModel;

import java.util.Set;

public interface UserRoleService {

    UserRoleServiceModel getRoleByName(String name);

    Set<UserRoleServiceModel> getAllRoles();
}
