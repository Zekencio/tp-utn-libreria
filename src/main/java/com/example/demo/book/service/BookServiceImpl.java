package com.example.demo.book.service;

import com.example.demo.author.model.Author;
import com.example.demo.author.repository.AuthorRepository;
import com.example.demo.book.dto.BookDTO;
import com.example.demo.book.dto.CreateBookDTO;
import com.example.demo.book.dto.UpdateBookDTO;
import com.example.demo.book.model.Book;
import com.example.demo.book.repository.BookRepository;
import com.example.demo.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository repository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository repository, AuthorRepository authorRepository) {
        this.repository = repository;
        this.authorRepository = authorRepository;
    }

    @Override
    public BookDTO createBook(CreateBookDTO createBookDTO) {
        Book newBook = convertToEntity(createBookDTO);
        Book savedBook= repository.save(newBook);
        return convertToDTO(savedBook);
    }

    @Override
    public List<BookDTO> getAll() {
        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<BookDTO> getById(Long id) {
        Optional<Book> book = repository.findById(id);
        return book.map(this::convertToDTO);
    }

    @Override
    public boolean delteBook(Long id) {
        Optional<Book> book = repository.findById(id);
        if (book.isPresent()){
            repository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Optional<BookDTO> updateBook(Long id, UpdateBookDTO updateBookDTO) {
        return repository.findById(id)
                .map(existing ->{
                    if (updateBookDTO.getName() != null){
                        existing.setName(updateBookDTO.getName());
                    }
                    if (updateBookDTO.getDescription() != null){
                        existing.setDescription(updateBookDTO.getDescription());
                    }
                    if (updateBookDTO.getPrice() != null){
                        existing.setPrice(updateBookDTO.getPrice());
                    }
                    if (updateBookDTO.getStock() != null){
                        existing.setStock(updateBookDTO.getStock());
                    }
                    if (updateBookDTO.getAuthor() != null){
                        existing.setAuthor(updateBookDTO.getAuthor());
                    }
                    if (updateBookDTO.getGenres() != null){
                        existing.setGenres(updateBookDTO.getGenres());
                    }
                    if (updateBookDTO.getSeller() != null){
                        existing.setSeller(updateBookDTO.getSeller());
                    }
                    Book saved = repository.save(existing);
                    return convertToDTO(saved);
                });
    }

    @Override
    public List<BookDTO> getByAuthor(Long id) throws NotFoundException {
        Optional<Author> autor= authorRepository.findById(id);
        if (autor.isPresent()) {
            return repository.findAll().stream().filter(book -> book.getAuthor().equals(autor.get())).map(this::convertToDTO).collect(Collectors.toList());
        }else {
            throw new NotFoundException("autor inexistente");
        }
    }


    @Override
    public Book convertToEntity(CreateBookDTO createBookDTO) {
        return new Book(createBookDTO.getName(), createBookDTO.getDescription(),createBookDTO.getPrice(), createBookDTO.getStock(),createBookDTO.getAuthor(),createBookDTO.getGenres(),createBookDTO.getSeller());
    }

    @Override
    public BookDTO convertToDTO(Book book) {
        return new BookDTO(book.getId(), book.getName(), book.getDescription(), book.getPrice(), book.getStock(), book.getAuthor(),book.getGenres(),book.getSeller());
    }
}
