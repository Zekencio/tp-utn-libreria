package com.example.demo.sellerprofile.dto;

import com.example.demo.book.model.Book;
import com.example.demo.user.model.User;

import java.util.List;
import java.util.Objects;

public class SellerProfileDTO {

    private Long id;
    private String name;
    private String address;
    private User sellerUser;

    public SellerProfileDTO(Long id, String name, String address, User sellerUser) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.sellerUser = sellerUser;
    }

    public SellerProfileDTO(String name, String address, List<Book> inventory, User sellerUser) {
        this.name = name;
        this.address = address;
        this.sellerUser = sellerUser;
    }

    public SellerProfileDTO() {
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
        SellerProfileDTO that = (SellerProfileDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(sellerUser, that.sellerUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, sellerUser);
    }

    @Override
    public String toString() {
        return "SellerProfileDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", sellerUser=" + sellerUser +
                '}';
    }
}
