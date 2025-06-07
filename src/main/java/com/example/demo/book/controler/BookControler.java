package com.example.demo.book.controler;

import com.example.demo.book.dto.BookDTO;
import com.example.demo.book.dto.CreateBookDTO;
import com.example.demo.book.dto.UpdateBookDTO;
import com.example.demo.book.service.BookServiceImpl;
import com.example.demo.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookControler {

    private final BookServiceImpl bookService;

    public BookControler(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks () {
        List<BookDTO> books = bookService.getAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@PathVariable Long id){
        List<BookDTO> books= new ArrayList<>();
        try {
            books= bookService.getByAuthor(id);
            return ResponseEntity.ok(books);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getbookById (@PathVariable Long id) {
        Optional<BookDTO> book = bookService.getById(id);
        return book.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody CreateBookDTO createBookDTO){
        BookDTO bookDTO = bookService.createBook(createBookDTO);
        return new ResponseEntity<>(bookDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody UpdateBookDTO updateBookDTO){
        Optional<BookDTO> updatedBook = bookService.updateBook(id, updateBookDTO);
        return updatedBook.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        boolean deleted = bookService.deleteBook(id);
        if (deleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
