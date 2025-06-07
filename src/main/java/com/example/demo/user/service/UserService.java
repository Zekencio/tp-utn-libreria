package com.example.demo.user.service;

import com.example.demo.user.dto.CreateUserDTO;
import com.example.demo.user.dto.UpdateUserDTO;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserDTO> getById(Long id);
    List<UserDTO> getAll();
    UserDTO createUser(CreateUserDTO createUserDTO);
    Optional<UserDTO> updateUser (Long id, UpdateUserDTO updateUserDTO);
    boolean deleteUser(Long id);

    User convertToEntity(CreateUserDTO createUserDTO);
    UserDTO convertToDTO(User user);
}
