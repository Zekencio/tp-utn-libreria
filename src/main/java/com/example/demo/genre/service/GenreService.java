package com.example.demo.genre.service;

import com.example.demo.author.dto.AuthorDTO;
import com.example.demo.author.dto.CreateAuthorDTO;
import com.example.demo.author.dto.UpdateAuthorDTO;
import com.example.demo.author.model.Author;
import com.example.demo.genre.dto.CreateGenreDTO;
import com.example.demo.genre.dto.GenreDTO;
import com.example.demo.genre.dto.UpdateGenreDTO;
import com.example.demo.genre.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    Optional<GenreDTO> getById(Long id);
    List<GenreDTO> getAll();
    GenreDTO createGenre(CreateGenreDTO createGenreDTO);
    Optional<GenreDTO> updateGenre (Long id, UpdateGenreDTO updateGenreDTO);
    boolean delteGenre(Long id);

    Genre convertToEntity(CreateGenreDTO createGenreDTO);
    GenreDTO convertToDTO(Genre genre);
}
