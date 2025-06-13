package com.example.demo.user.service;

import com.example.demo.configuration.CurrentUserUtils;
import com.example.demo.exceptions.AlreadyExistingException;
import com.example.demo.user.dto.CreateUserDTO;
import com.example.demo.user.dto.UpdateUserDTO;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.model.User;
import com.example.demo.user.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
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


    public Optional<User> getByname() {
        String name= CurrentUserUtils.obtenerUsername();
        return repository.findByName(name);
    }

    @Override
    public UserDTO createUser(CreateUserDTO createUserDTO) throws AlreadyExistingException {
        User newUser = convertToEntity(createUserDTO);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        if (repository.findAll().contains(newUser)) {
            throw new AlreadyExistingException("El usuario ya existe");
        }
        User savedUser = repository.save(newUser);
        return convertToDTO(savedUser);
    }

    @Override
    public boolean deleteUser(Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<UserDTO> updateUser(Long id, UpdateUserDTO updateUserDTO) {
        return repository.findById(id)
                .map(existing -> {
                    if (updateUserDTO.getName() != null) {
                        existing.setName(updateUserDTO.getName());
                    }
                    if (updateUserDTO.getPassword() != null) {
                        existing.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
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
        return new UserDTO(user.getId(), user.getName(), user.getPassword());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
