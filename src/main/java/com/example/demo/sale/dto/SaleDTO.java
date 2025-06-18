package com.example.demo.sale.dto;

import com.example.demo.book.dto.BookDTOReduced;
import com.example.demo.book.model.Book;
import com.example.demo.cards.model.Card;
import com.example.demo.user.model.User;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class SaleDTO {

    private Long id;
    private Date date;
    private User user;
    private Card card;
    private List<BookDTOReduced> books;

    public SaleDTO(Long id, Date date, User user, Card card, List<BookDTOReduced> books) {
        this.id = id;
        this.date = date;
        this.user = user;
        this.card = card;
        this.books = books;
    }

    public SaleDTO(Date date, User user, Card card, List<BookDTOReduced> books) {
        this.date = date;
        this.user = user;
        this.card = card;
        this.books = books;
    }

    public SaleDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public List<BookDTOReduced> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTOReduced> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleDTO saleDTO = (SaleDTO) o;
        return Objects.equals(date, saleDTO.date) && Objects.equals(user, saleDTO.user) && Objects.equals(card, saleDTO.card) && Objects.equals(books, saleDTO.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, user, card, books);
    }

    @Override
    public String toString() {
        return "SaleDTO{" +
                "id=" + id +
                ", date=" + date +
                ", user=" + user +
                ", card=" + card +
                ", books=" + books +
                '}';
    }
}
