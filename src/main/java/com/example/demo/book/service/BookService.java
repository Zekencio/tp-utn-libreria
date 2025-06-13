package com.example.demo.book.service;

import com.example.demo.book.dto.BookDTO;
import com.example.demo.book.dto.BookDTOReduced;
import com.example.demo.book.dto.CreateBookDTO;
import com.example.demo.book.dto.UpdateBookDTO;
import com.example.demo.book.model.Book;
import com.example.demo.exceptions.AlreadyExistingException;
import com.example.demo.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<BookDTO> getById(Long id);
    List<BookDTOReduced> getAll();
    BookDTO createBook(CreateBookDTO createBookDTO) throws AlreadyExistingException;
    Optional<BookDTO> updateBook (Long id, UpdateBookDTO updateBookDTO);
    boolean deleteBook(Long id);

    List<BookDTO> getByAuthor(Long id) throws NotFoundException;

    Book convertToEntity(CreateBookDTO createBookDTO);
    BookDTO convertToDTO(Book book);
}
