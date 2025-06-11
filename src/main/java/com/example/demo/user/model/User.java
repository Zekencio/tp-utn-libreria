package com.example.demo.user.model;

import com.example.demo.book.model.Book;
import com.example.demo.cards.model.Card;
import com.example.demo.sale.model.Sale;
import com.example.demo.sellerprofile.model.SellerProfile;
import jakarta.persistence.*;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "cartUser")
    private List<Book> cart;

    @OneToMany(mappedBy = "user")
    private List<Sale> sales;

    @OneToOne(mappedBy = "sellerUser")
    private SellerProfile sellerProfile;

    @OneToMany(mappedBy = "owner")
    private List<Card> cards;

    public User(Long id, String name, String password, List<Book> cart, List<Sale> sales, SellerProfile sellerProfile, List<Card> cards) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.cart = cart;
        this.sales = sales;
        this.sellerProfile = sellerProfile;
        this.cards = cards;
    }

    public User(String name, String password, List<Book> cart, List<Sale> sales, SellerProfile sellerProfile, List<Card> cards) {
        this.name = name;
        this.password = password;
        this.cart = cart;
        this.sales = sales;
        this.sellerProfile = sellerProfile;
        this.cards = cards;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User() {
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
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(password, user.password) && Objects.equals(cart, user.cart) && Objects.equals(sales, user.sales) && Objects.equals(sellerProfile, user.sellerProfile) && Objects.equals(cards, user.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, cart, sales, sellerProfile, cards);
    }

    @Override
    public String toString() {
        return "User{" +
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
