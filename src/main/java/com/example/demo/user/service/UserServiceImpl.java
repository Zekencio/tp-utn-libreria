package com.example.demo.user.service;
import com.example.demo.configuration.CurrentUserUtils;
import com.example.demo.exceptions.AlreadyExistingException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.user.dto.CreateUserDTO;
import com.example.demo.user.dto.UpdateUserDTO;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.sellerprofile.dto.SellerProfileDTOFull;
import com.example.demo.book.dto.BookDTOReduced;
import com.example.demo.book.model.Book;
import com.example.demo.user.model.User;
import com.example.demo.user.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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


    public User getCurrentUser() throws NotFoundException {
        String name= CurrentUserUtils.getUsername();
        Optional<User> user = repository.findByName(name);
        if (user.isEmpty()){
            throw new NotFoundException("Necesitas iniciar sesion");
        }
        return user.get();
    }

    @Override
    public UserDTO createUser(CreateUserDTO createUserDTO) throws AlreadyExistingException {
        User newUser = convertToEntity(createUserDTO);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        if (repository.findByName(newUser.getName()).isPresent()) {
            throw new AlreadyExistingException("El usuario ya existe");
        }
        newUser.getRoles().add("ROLE_CLIENT");
        User savedUser = repository.save(newUser);
        return convertToDTO(savedUser);
    }

    @Override
    public boolean deleteUser() throws NotFoundException {
        User user = getCurrentUser();
        repository.deleteById(user.getId());
        return true;
    }

    @Override
    public Optional<UserDTO> updateUser(UpdateUserDTO updateUserDTO) throws NotFoundException, com.example.demo.exceptions.UnautorizedException {
        User user = getCurrentUser();
        Optional<User> optional = repository.findById(user.getId());
        if (optional.isEmpty()) {
            return Optional.empty();
        }
        User existing = optional.get();
        boolean wantsNameChange = updateUserDTO.getName() != null;
        boolean wantsPasswordChange = updateUserDTO.getPassword() != null;
        if (wantsNameChange || wantsPasswordChange) {
            String currentProvided = updateUserDTO.getCurrentPassword();
            if (currentProvided == null || !passwordEncoder.matches(currentProvided, existing.getPassword())) {
                throw new com.example.demo.exceptions.UnautorizedException("Current password is invalid");
            }
        }

        if (wantsNameChange) {
            existing.setName(updateUserDTO.getName());
        }
        if (wantsPasswordChange) {
            existing.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
        }
        User saved = repository.save(existing);
        return Optional.of(convertToDTO(saved));
    }

    public Optional<UserDTO> updateSpecificUser(Long id, UpdateUserDTO updateUserDTO) {
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

    public void saveCurrentUser(User user) {
        repository.save(user);
    }

    public List<Book> addToUserCart(Book book, Integer cant) throws NotFoundException {
        User user = getCurrentUser();
        List<Book> bookList = user.getCart();
        if (bookList == null){
            bookList=new ArrayList<>();
        }
        for (int i =0; i<cant; i++){
            bookList.add(book);
        }
        user.setCart(bookList);
        return repository.save(user).getCart();
    }

    public List<Book> removeFromUserCart(Book book, Integer cant) throws NotFoundException {
        User user = getCurrentUser();
        List<Book> bookList = user.getCart();
        for (int i =0; i<cant; i++){
            bookList.remove(book);
        }
        user.setCart(bookList);
        return repository.save(user).getCart();
    }

    public void emptyCart() throws NotFoundException {
        User user = getCurrentUser();
        user.setCart(new ArrayList<Book>());
        repository.save(user);
    }

    @Override
    public User convertToEntity(CreateUserDTO createUserDTO) {
        return new User(createUserDTO.getName(), createUserDTO.getPassword());
    }

    @Override
    public UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO(user.getId(), user.getName(), user.getRoles());
        if (user.getSellerProfile() != null) {
            var sp = user.getSellerProfile();
        java.util.List<BookDTOReduced> books = sp.getInventory() == null ? java.util.Collections.<BookDTOReduced>emptyList() : sp.getInventory().stream()
            .map(b -> new BookDTOReduced(b.getId(), b.getName(), b.getDescription()))
            .collect(java.util.stream.Collectors.toList());
            SellerProfileDTOFull sellerDto = new SellerProfileDTOFull(sp.getId(), sp.getName(), sp.getAddress(), books);
            dto.setSellerProfile(sellerDto);
        }
        return dto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

    List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());

    return new org.springframework.security.core.userdetails.User(
        user.getName(),
        user.getPassword(),
        authorities
    );
    }
}
