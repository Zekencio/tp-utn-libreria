package com.example.demo.user.service;

import com.example.demo.user.dto.CreateUserDTO;
import com.example.demo.user.dto.UpdateUserDTO;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.model.User;
import com.example.demo.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService{

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserDTO> getAll() {
        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getById(Long id) {
        Optional<User> user = repository.findById(id);
        return user.map(this::convertToDTO);
    }

    @Override
    public UserDTO createUser(CreateUserDTO createUserDTO) {
        User newUser = convertToEntity(createUserDTO);
        User savedUser = repository.save(newUser);
        return convertToDTO(savedUser);
    }

    @Override
    public boolean delteUser(Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()){
            return false;
        }else {
            repository.deleteById(id);
            return true;
        }
    }

    @Override
    public Optional<UserDTO> updateUser(Long id, UpdateUserDTO updateUserDTO) {
        return repository.findById(id)
                .map(existing ->{
                    if (updateUserDTO.getName() != null){
                        existing.setName(updateUserDTO.getName());
                    }
                    if (updateUserDTO.getPassword() != null){
                        existing.setPassword(updateUserDTO.getPassword());
                    }
                    User saved = repository.save(existing);
                    return convertToDTO(saved);
                });
    }

    @Override
    public User convertToEntity(CreateUserDTO createUserDTO) {
        return new User(createUserDTO.getName(), createUserDTO.getPassword());
    }

    @Override
    public UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(),user.getName(), user.getPassword());
    }
}
