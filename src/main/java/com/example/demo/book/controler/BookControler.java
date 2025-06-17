package com.example.demo.book.controler;

import com.example.demo.book.dto.BookDTO;
import com.example.demo.book.dto.BookDTOReduced;
import com.example.demo.book.dto.CreateBookDTO;
import com.example.demo.book.dto.UpdateBookDTO;
import com.example.demo.book.service.BookServiceImpl;
import com.example.demo.exceptions.AlreadyExistingException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.exceptions.UnautorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookControler {

    private final BookServiceImpl bookService;

    public BookControler(BookServiceImpl bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTOReduced>> getAllBooks () {
        List<BookDTOReduced> books = bookService.getAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@PathVariable Long id){
        List<BookDTO> books;
        try {
            books = bookService.getByAuthor(id);
            return ResponseEntity.ok(books);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        Optional<BookDTO> book = bookService.getById(id);
        return book.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/genre/{id}")
    public ResponseEntity<List<BookDTO>> getBooksByGenre(@PathVariable Long id) {
        List<BookDTO> books;
        try{
            books = bookService.getByGenre(id);
            return ResponseEntity.ok(books);
        }catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody CreateBookDTO createBookDTO){
        try {
            BookDTO bookDTO = bookService.createBook(createBookDTO);
            return new ResponseEntity<>(bookDTO, HttpStatus.CREATED);
        }catch (AlreadyExistingException | NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody UpdateBookDTO updateBookDTO){
        try {
            Optional<BookDTO> updatedBook = bookService.updateBook(id, updateBookDTO);
            return updatedBook.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }catch (UnautorizedException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        try {
            boolean deleted = bookService.deleteBook(id);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return ResponseEntity.notFound().build();
            }
        }catch (UnautorizedException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/add/{id}")
    public ResponseEntity<Void> addBookToCart(@PathVariable Long id,@RequestBody Integer cant){
        try{
            bookService.addToCart(id,cant);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }catch (ArithmeticException e){
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/remove/{id}")
    public ResponseEntity<Void> removeBookfromCart(@PathVariable Long id,@RequestBody Integer cant){
        try{
            bookService.removeFromCart(id,cant);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }catch (ArithmeticException e){
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/statistics/averageprice")
    public ResponseEntity<Double> getAveragePrice(){
        return ResponseEntity.ok(bookService.calculateAveragePrice());
    }

    @GetMapping("/statistics/books-per-author")
    public ResponseEntity<Map<String, Long>> getBooksPerAuthor(){
        return ResponseEntity.ok(bookService.countBooksPerAuthor());
    }


}
