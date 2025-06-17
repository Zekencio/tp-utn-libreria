package com.example.demo.book.service;

import com.example.demo.author.dto.AuthorDTOReduced;
import com.example.demo.author.model.Author;
import com.example.demo.author.repository.AuthorRepository;
import com.example.demo.book.dto.BookDTO;
import com.example.demo.book.dto.BookDTOReduced;
import com.example.demo.book.dto.CreateBookDTO;
import com.example.demo.book.dto.UpdateBookDTO;
import com.example.demo.book.model.Book;
import com.example.demo.book.repository.BookRepository;
import com.example.demo.configuration.CurrentUserUtils;
import com.example.demo.exceptions.AlreadyExistingException;
import com.example.demo.exceptions.InsufficientStockException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.exceptions.UnautorizedException;
import com.example.demo.user.model.User;
import com.example.demo.user.service.UserServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository repository;
    private final AuthorRepository authorRepository;
    private final UserServiceImpl userService;

    public BookServiceImpl(BookRepository repository, AuthorRepository authorRepository, UserServiceImpl userService) {
        this.repository = repository;
        this.authorRepository = authorRepository;
        this.userService = userService;
    }

    @Override
    public BookDTO createBook(CreateBookDTO createBookDTO) throws AlreadyExistingException, NotFoundException {
        Book newBook = convertToEntity(createBookDTO);
        User user =userService.getCurrentUser();
        newBook.setSeller(user.getSellerProfile());
        if(repository.findAll().contains(newBook)){
            throw new AlreadyExistingException("Este libro ye existe");
        }
        Book savedBook= repository.save(newBook);
        return convertToDTO(savedBook);
    }

    @Override
    public List<BookDTOReduced> getAll() {
        return repository.findAll().stream().map(this::reduceBook).collect(Collectors.toList());
    }

    @Override
    public Optional<BookDTO> getById(Long id) {
        Optional<Book> book = repository.findById(id);
        return book.map(this::convertToDTO);
    }

    @Override
    public boolean deleteBook(Long id) {
        Optional<Book> book = repository.findById(id);
        if (book.isPresent()){
            repository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

    public void addToCart (Long id, Integer cant) throws NotFoundException {
        Optional<Book> book = repository.findById(id);
        if(book.isEmpty()){
            throw new NotFoundException("Libro no encontrado");
        }else if (cant <=0){
            throw new ArithmeticException("La cantidad no puede ser 0 o menor");
        }
        userService.addToUserCart(book.get(),cant);
    }

    public void removeFromCart (Long id, Integer cant) throws NotFoundException {
        Optional<Book> book = repository.findById(id);
        if(book.isEmpty()){
            throw new NotFoundException("Libro no encontrado");
        }else if (cant <=0){
            throw new ArithmeticException("La cantidad no puede ser 0 o menor");
        }
        userService.removeFromUserCart(book.get(),cant);
    }

    @Override
    public double calculateAveragePrice() {
        return repository.findAll().stream()
                .mapToDouble(Book::getPrice)
                .average().orElse(0.0);
    }

    @Override
    public Map<String, Long> countBooksPerAuthor() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(book -> book.getAuthor().getName(),
                        Collectors.counting()));
    }

    @Override
    public Optional<BookDTO> updateBook(Long id, UpdateBookDTO updateBookDTO) throws UnautorizedException {
        Optional<Book> book = repository.findById(id);
        if (book.isPresent() && !book.get().getSeller().getSellerUser().getName().equals(CurrentUserUtils.obtenerUsername())){
            throw new UnautorizedException("No esta autorizado para realizar esta acicon");
        }
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

    public void updateStock(List<Book> cart) throws InsufficientStockException, NotFoundException {
        while (!cart.isEmpty()){
            Book book = cart.getFirst();
            cart.remove(book);
            if (book.getStock() == 0){
                throw new InsufficientStockException("stock isuficiente");
            }
            if (repository.existsById(book.getId())){
                book.setStock(book.getStock()-1);
                repository.save(book);
            }else{
                throw new NotFoundException("El libro no esta disponible");
            }


        }
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
        return new BookDTO(book.getId(), book.getName(), book.getDescription(), book.getPrice(), book.getStock(),reduceAuthor(book.getAuthor()),book.getGenres(),book.getSeller());
    }

    private AuthorDTOReduced reduceAuthor(Author author){
        return new AuthorDTOReduced(author.getId(), author.getName(),author.getBirthDate());
    }
    private BookDTOReduced reduceBook(Book book){
        return new BookDTOReduced(book.getId(), book.getName(), book.getDescription());
    }

}
