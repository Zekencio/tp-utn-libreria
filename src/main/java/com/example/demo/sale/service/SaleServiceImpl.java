package com.example.demo.sale.service;

import com.example.demo.book.model.Book;
import com.example.demo.book.service.BookServiceImpl;
import com.example.demo.cards.dto.CardDTO;
import com.example.demo.cards.model.Card;
import com.example.demo.cards.service.CardServiceImpl;
import com.example.demo.configuration.CurrentUserUtils;
import com.example.demo.exceptions.InsufficientStockException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.exceptions.UnautorizedException;
import com.example.demo.sale.dto.CreateSaleDTO;
import com.example.demo.sale.dto.SaleDTO;
import com.example.demo.sale.dto.UpdateSaleDTO;
import com.example.demo.sale.model.Sale;
import com.example.demo.sale.repository.SaleRepository;
import com.example.demo.user.model.User;
import com.example.demo.user.service.UserServiceImpl;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService{

    private final SaleRepository repository;
    private final UserServiceImpl userService;
    private final CardServiceImpl cardService;
    private final BookServiceImpl bookService;

    public SaleServiceImpl(SaleRepository repository, UserServiceImpl userService, CardServiceImpl cardService, BookServiceImpl bookService) {
        this.repository = repository;
        this.userService = userService;
        this.cardService = cardService;
        this.bookService = bookService;
    }

    @Override
    public List<SaleDTO> getAll() {
        return repository.findAll().stream().filter(sale -> sale.getUser().getName().equals(CurrentUserUtils.getUsername())).map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<SaleDTO> getById(Long id) {
        Optional<Sale> sale = repository.findById(id);
        if (sale.get().getUser().getName().equals(CurrentUserUtils.getUsername())){
            return sale.map(this::convertToDTO);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public SaleDTO createSale(Long id) throws NotFoundException, InsufficientStockException{
        User user = userService.getCurrentUser();
        Optional<Card> card = cardService.getByIdNumber(id);

        if(card.isPresent() && user.getCards().contains(card.get())){
            List<Book> cart = new ArrayList<>(user.getCart());

            Sale newSale = new Sale(Date.valueOf(LocalDate.now()),user,card.get(),cart);
            Sale savedSale = repository.save(newSale);
            bookService.updateStock(user.getCart());

            userService.emptyCart();
            return convertToDTO(savedSale);
        }else {
            throw new NotFoundException("La tarjeta no esta registrada");
        }
    }

    @Override
    public boolean deleteSale(Long id) {
        Optional<Sale> sale = repository.findById(id);
        if (sale.isPresent()){
            repository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Optional<SaleDTO> updateSale(Long id, UpdateSaleDTO updateSaleDTO) {
        return repository.findById(id)
                .map(existing ->{
                    if (updateSaleDTO.getDate() != null){
                        existing.setDate(updateSaleDTO.getDate());
                    }
                    if (updateSaleDTO.getUser() != null){
                        existing.setUser(updateSaleDTO.getUser());
                    }
                    if (updateSaleDTO.getCard() != null){
                        existing.setCard(updateSaleDTO.getCard());
                    }
                    if (updateSaleDTO.getBooks() != null){
                        existing.setBooks(updateSaleDTO.getBooks());
                    }
                    Sale sale = repository.save(existing);
                    return convertToDTO(sale);
                });
    }

    @Override
    public Sale convertToEntity(CreateSaleDTO createSaleDTO) {
        return new Sale(createSaleDTO.getDate(),createSaleDTO.getUser(),createSaleDTO.getCard(),createSaleDTO.getBooks());
    }

    @Override
    public SaleDTO convertToDTO(Sale sale) {
        return new SaleDTO(sale.getId(),sale.getDate(),userService.convertToDTO(sale.getUser()),cardService.reduceCard(sale.getCard()),sale.getBooks().stream().map(bookService::reduceBook).collect(Collectors.toList()));
    }


}
