package com.example.demo.user.service;

import com.example.demo.exceptions.AlreadyExistingException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.user.dto.CreateUserDTO;
import com.example.demo.user.dto.UpdateUserDTO;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserDTO> getById(Long id);
    List<UserDTO> getAll();
    UserDTO createUser(CreateUserDTO createUserDTO) throws AlreadyExistingException;
    Optional<UserDTO> updateUser (UpdateUserDTO updateUserDTO) throws NotFoundException;
    boolean deleteUser(Long id);

    User convertToEntity(CreateUserDTO createUserDTO);
    UserDTO convertToDTO(User user);
}
