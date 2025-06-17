package com.example.demo.genre.service;

import com.example.demo.exceptions.AlreadyExistingException;
import com.example.demo.genre.dto.CreateGenreDTO;
import com.example.demo.genre.dto.GenreDTO;
import com.example.demo.genre.dto.UpdateGenreDTO;
import com.example.demo.genre.model.Genre;
import com.example.demo.genre.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService{

    private final GenreRepository repository;

    public GenreServiceImpl(GenreRepository repository) {
        this.repository = repository;
    }

    @Override
    public GenreDTO createGenre(CreateGenreDTO createGenreDTO) throws AlreadyExistingException {
        Genre newGenre = convertToEntity(createGenreDTO);
        if (repository.findAll().contains(newGenre)){
            throw new AlreadyExistingException("El genreo ya existe");
        }
        Genre savedGenre = repository.save(newGenre);
        return convertToDTO(savedGenre);
    }

    @Override
    public List<GenreDTO> getAll() {
        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<GenreDTO> getById(Long id) {
        Optional<Genre> genre = repository.findById(id);
        return genre.map(this::convertToDTO);
    }

    public Optional<Genre> getEntityById(Long id) {
       return repository.findById(id);
    }

    @Override
    public boolean deleteGenre(Long id) {
        Optional<Genre> genre = repository.findById(id);
        if (genre.isPresent()){
            repository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Optional<GenreDTO> updateGenre(Long id, UpdateGenreDTO updateGenreDTO) {
        return repository.findById(id)
                .map(existing ->{
                    if (updateGenreDTO.getName() != null){
                        existing.setName(updateGenreDTO.getName());
                    }
                    if (updateGenreDTO.getDescription() != null){
                        existing.setDescription(updateGenreDTO.getDescription());
                    }
                    Genre genre = repository.save(existing);
                    return convertToDTO(genre);
                });
    }

    @Override
    public Genre convertToEntity(CreateGenreDTO createGenreDTO) {
        return new Genre(createGenreDTO.getName(), createGenreDTO.getDescription());
    }

    @Override
    public GenreDTO convertToDTO(Genre genre) {
        return new GenreDTO(genre.getId() , genre.getName(),genre.getDescription());
    }
}
