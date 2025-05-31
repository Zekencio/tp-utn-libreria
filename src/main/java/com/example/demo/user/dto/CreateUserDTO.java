package com.example.demo.user.dto;

import com.example.demo.book.model.Book;
import com.example.demo.cards.model.Card;
import com.example.demo.sale.model.Sale;
import com.example.demo.sellerprofile.model.SellerProfile;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Objects;

public class CreateUserDTO {

    @NotBlank(message = "The username cannot be blank")
    private String name;
    @NotBlank(message = "The password cannot be blank")
    private String password;
    private List<Book> cart;
    private List<Sale> sales;
    private SellerProfile sellerProfile;
    private List<Card> cards;

    public CreateUserDTO(String name, String password, List<Book> cart, List<Sale> sales, SellerProfile sellerProfile, List<Card> cards) {
        this.name = name;
        this.password = password;
        this.cart = cart;
        this.sales = sales;
        this.sellerProfile = sellerProfile;
        this.cards = cards;
    }

    public CreateUserDTO() {
    }

    public @NotBlank(message = "The username cannot be blank") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "The username cannot be blank") String name) {
        this.name = name;
    }

    public @NotBlank(message = "The password cannot be blank") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "The password cannot be blank") String password) {
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
        CreateUserDTO that = (CreateUserDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(password, that.password) && Objects.equals(cart, that.cart) && Objects.equals(sales, that.sales) && Objects.equals(sellerProfile, that.sellerProfile) && Objects.equals(cards, that.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, cart, sales, sellerProfile, cards);
    }

    @Override
    public String toString() {
        return "CreateUserDTO{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", cart=" + cart +
                ", sales=" + sales +
                ", sellerProfile=" + sellerProfile +
                ", cards=" + cards +
                '}';
    }
}
