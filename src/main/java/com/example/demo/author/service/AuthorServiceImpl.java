package com.example.demo.author.service;

import com.example.demo.author.dto.AuthorDTO;
import com.example.demo.author.dto.CreateAuthorDTO;
import com.example.demo.author.dto.UpdateAuthorDTO;
import com.example.demo.author.model.Author;
import com.example.demo.author.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public AuthorDTO createAuthor(CreateAuthorDTO createAuthorDTO) {
        Author newAuthor = convertToEntity(createAuthorDTO);
        Author savedAuthor= repository.save(newAuthor);
        return convertToDTO(savedAuthor);
    }

    @Override
    public List<AuthorDTO> getAll() {
        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public boolean deleteAuthor(Long id) {
        Optional<Author> author = repository.findById(id);
        if (author.isPresent()){
            repository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Optional<AuthorDTO> getById(Long id) {
        Optional<Author> author = repository.findById(id);
        return author.map(this::convertToDTO);
    }

    @Override
    public Optional<AuthorDTO> updateAuthor(Long id, UpdateAuthorDTO updateAuthorDTO) {
        return repository.findById(id)
                .map(existing ->{
                    if (updateAuthorDTO.getName() != null){
                        existing.setName(updateAuthorDTO.getName());
                    }
                    if (updateAuthorDTO.getBirthDate() != null){
                        existing.setBirthDate(updateAuthorDTO.getBirthDate());
                    }
                    Author saved = repository.save(existing);
                    return convertToDTO(saved);
                });
    }

    @Override
    public Author convertToEntity(CreateAuthorDTO createAuthorDTO) {
        return new Author(createAuthorDTO.getName(),createAuthorDTO.getBirthDate());
    }

    @Override
    public AuthorDTO convertToDTO(Author author) {
        return new AuthorDTO(author.getId(),author.getName(),author.getBirthDate(),author.getBookslist());
    }
}
