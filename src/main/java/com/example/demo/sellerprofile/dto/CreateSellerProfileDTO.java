package com.example.demo.sellerprofile.dto;

import com.example.demo.book.model.Book;
import com.example.demo.user.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;

public class CreateSellerProfileDTO {

    @NotBlank(message = "The seller name cannot be empty")
    @jakarta.validation.constraints.Size(min = 6, message = "The seller name must be at least 6 characters")
    private String name;
    @NotBlank(message = "The address cannot be empty")
    @jakarta.validation.constraints.Size(min = 6, message = "The address must be at least 6 characters")
    private String address;
    private User sellerUser;

    public CreateSellerProfileDTO(String name, String address, User sellerUser) {
        this.name = name;
        this.address = address;
        this.sellerUser = sellerUser;
    }

    public CreateSellerProfileDTO() {
    }

    public @NotBlank(message = "The name of the seller cannot be empty") @jakarta.validation.constraints.Size(min = 6, message = "The seller name must be at least 6 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "The name of the seller cannot be empty") @jakarta.validation.constraints.Size(min = 6, message = "The seller name must be at least 6 characters") String name) {
        this.name = name;
    }

    public @NotBlank(message = "The address cannot be empty") @jakarta.validation.constraints.Size(min = 6, message = "The address must be at least 6 characters") String getAddress() {
        return address;
    }

    public void setAddress(@NotBlank(message = "The address cannot be empty") @jakarta.validation.constraints.Size(min = 6, message = "The address must be at least 6 characters") String address) {
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
        CreateSellerProfileDTO that = (CreateSellerProfileDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(sellerUser, that.sellerUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, sellerUser);
    }

    @Override
    public String toString() {
        return "CreateSellerProfileDTO{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", sellerUser=" + sellerUser +
                '}';
    }
}
