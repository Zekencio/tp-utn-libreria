package com.example.demo.book.service;

import com.example.demo.book.dto.BookDTO;
import com.example.demo.book.dto.CreateBookDTO;
import com.example.demo.book.dto.UpdateBookDTO;
import com.example.demo.book.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<BookDTO> getById(Long id);
    List<BookDTO> getAll();
    BookDTO createBook(CreateBookDTO createBookDTO);
    Optional<BookDTO> updateBook (Long id, UpdateBookDTO updateBookDTO);
    boolean delteBook(Long id);

    Book convertToEntity(CreateBookDTO createBookDTO);
    BookDTO convertToDTO(Book book);
}
