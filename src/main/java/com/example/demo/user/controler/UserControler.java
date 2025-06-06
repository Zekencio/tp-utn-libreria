package com.example.demo.user.controler;

import com.example.demo.book.dto.BookDTO;
import com.example.demo.book.dto.CreateBookDTO;
import com.example.demo.book.dto.UpdateBookDTO;
import com.example.demo.user.dto.CreateUserDTO;
import com.example.demo.user.dto.UpdateUserDTO;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserControler {

    private final UserServiceImpl userService;

    public UserControler(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers () {
        List<UserDTO> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById (@PathVariable Long id) {
        Optional<UserDTO> user = userService.getById(id);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserDTO createUserDTO){
        UserDTO userDTO = userService.createUser(createUserDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UpdateUserDTO updateUserDTO){
        Optional<UserDTO> updatedUser = userService.updateUser(id, updateUserDTO);
        return updatedUser.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        boolean deleted = userService.delteUser(id);
        if (deleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
