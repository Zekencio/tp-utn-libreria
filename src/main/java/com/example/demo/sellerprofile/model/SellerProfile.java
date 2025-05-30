package com.example.demo.sellerprofile.model;

import com.example.demo.book.model.Book;
import com.example.demo.user.model.User;
import jakarta.persistence.*;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "sellers")
public class SellerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String address;

    @OneToMany
    private List<Book> Inventory;

    @OneToOne
    private User sellerUser;

    public SellerProfile(Long id, String name, String address, List<Book> inventory, User sellerUser) {
        this.id = id;
        this.name = name;
        this.address = address;
        Inventory = inventory;
        this.sellerUser = sellerUser;
    }

    public SellerProfile(String name, String address, List<Book> inventory, User sellerUser) {
        this.name = name;
        this.address = address;
        Inventory = inventory;
        this.sellerUser = sellerUser;
    }

    public SellerProfile() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Book> getInventory() {
        return Inventory;
    }

    public void setInventory(List<Book> inventory) {
        Inventory = inventory;
    }

    public User getSellerUser() {
        return sellerUser;
    }

    public void setSellerUser(User sellerUser) {
        this.sellerUser = sellerUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SellerProfile that = (SellerProfile) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(Inventory, that.Inventory) && Objects.equals(sellerUser, that.sellerUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, Inventory, sellerUser);
    }

    @Override
    public String toString() {
        return "SellerProfile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", Inventory=" + Inventory +
                ", sellerUser=" + sellerUser +
                '}';
    }
}
