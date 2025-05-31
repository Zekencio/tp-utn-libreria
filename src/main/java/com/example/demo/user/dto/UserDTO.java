package com.example.demo.user.dto;

import com.example.demo.book.model.Book;
import com.example.demo.cards.model.Card;
import com.example.demo.sale.model.Sale;
import com.example.demo.sellerprofile.model.SellerProfile;

import java.util.List;
import java.util.Objects;

public class UserDTO {

    private Long id;
    private String name;
    private String password;
    private List<Book> cart;
    private List<Sale> sales;
    private SellerProfile sellerProfile;
    private List<Card> cards;

    public UserDTO(Long id, String name, String password, List<Book> cart, List<Sale> sales, SellerProfile sellerProfile, List<Card> cards) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.cart = cart;
        this.sales = sales;
        this.sellerProfile = sellerProfile;
        this.cards = cards;
    }

    public UserDTO(String name, String password, List<Book> cart, List<Sale> sales, SellerProfile sellerProfile, List<Card> cards) {
        this.name = name;
        this.password = password;
        this.cart = cart;
        this.sales = sales;
        this.sellerProfile = sellerProfile;
        this.cards = cards;
    }

    public UserDTO() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Book> getCart() {
        return cart;
    }

    public void setCart(List<Book> cart) {
        this.cart = cart;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    public SellerProfile getSellerProfile() {
        return sellerProfile;
    }

    public void setSellerProfile(SellerProfile sellerProfile) {
        this.sellerProfile = sellerProfile;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id) && Objects.equals(name, userDTO.name) && Objects.equals(password, userDTO.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", cart=" + cart +
                ", sales=" + sales +
                ", sellerProfile=" + sellerProfile +
                ", cards=" + cards +
                '}';
    }
}
