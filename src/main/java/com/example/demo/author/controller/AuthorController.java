package com.example.demo.author.controller;

import com.example.demo.author.dto.AuthorDTO;
import com.example.demo.author.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors () {
        List<AuthorDTO> authors = authorService.getAll();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById (@PathVariable Long id) {
        Optional<AuthorDTO> author = authorService.getById(id);
        return author.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }



}
