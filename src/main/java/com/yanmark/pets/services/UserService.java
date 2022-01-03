package com.yanmark.pets.services;

import com.yanmark.pets.domain.models.bindings.users.UserEditBindingModel;
import com.yanmark.pets.domain.models.bindings.users.UserRegisterBindingModel;
import com.yanmark.pets.domain.models.services.UserRoleServiceModel;
import com.yanmark.pets.domain.models.services.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

  UserServiceModel saveUser(UserRegisterBindingModel userRegister);

  UserServiceModel updateUsersRole(
      UserServiceModel userService, UserRoleServiceModel userRoleService);

  UserServiceModel updateUser(UserServiceModel userService, UserEditBindingModel userEdit);

  UserServiceModel getUserById(String id);

  UserServiceModel getUserByUsername(String username);

  List<UserServiceModel> getAllUsers();
}
