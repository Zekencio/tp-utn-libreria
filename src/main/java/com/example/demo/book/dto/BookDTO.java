package com.example.demo.book.dto;

import com.example.demo.author.model.Author;
import com.example.demo.genre.model.Genre;
import com.example.demo.sale.model.Sale;
import com.example.demo.sellerprofile.model.SellerProfile;
import com.example.demo.user.model.User;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class BookDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Long stock;
    private Author author;
    private Set<Genre> genres;
    private List<Sale> sales;
    private SellerProfile seller;
    private User cartUser;

    public BookDTO(Long id, String name, String description, Double price, Long stock, Author author,
                   Set<Genre> genres, List<Sale> sales, SellerProfile seller, User cartUser) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.author = author;
        this.genres = genres;
        this.sales = sales;
        this.seller = seller;
        this.cartUser = cartUser;
    }

    public BookDTO(String name, String description, Double price, Long stock, Author author,
                   Set<Genre> genres, List<Sale> sales, SellerProfile seller, User cartUser) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.author = author;
        this.genres = genres;
        this.sales = sales;
        this.seller = seller;
        this.cartUser = cartUser;
    }

    public BookDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    public SellerProfile getSeller() {
        return seller;
    }

    public void setSeller(SellerProfile seller) {
        this.seller = seller;
    }

    public User getCartUser() {
        return cartUser;
    }

    public void setCartUser(User cartUser) {
        this.cartUser = cartUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDTO bookDTO = (BookDTO) o;
        return Objects.equals(name, bookDTO.name) && Objects.equals(author, bookDTO.author) && Objects.equals(genres, bookDTO.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author, genres);
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", author=" + author +
                ", genres=" + genres +
                '}';
    }
}
