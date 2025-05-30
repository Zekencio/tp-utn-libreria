package com.example.demo.sellerprofile.dto;

import com.example.demo.book.model.Book;
import com.example.demo.user.model.User;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Objects;

public class UpdateSellerProfileDTO {

    @NotBlank(message = "The seller name cannot be empty")
    @Size(min = 3, max = 255, message = "The seller name must have between 3 and 255 characters")
    private String name;
    @NotBlank(message = "The address cannot be empty")
    @Size(min = 3, max = 128, message = "The address must have between 3 and 128 characters")
    private String address;
    private List<Book> Inventory;
    private User sellerUser;

    public UpdateSellerProfileDTO(String name, String address, List<Book> inventory, User sellerUser) {
        this.name = name;
        this.address = address;
        Inventory = inventory;
        this.sellerUser = sellerUser;
    }

    public UpdateSellerProfileDTO() {
    }

    public @NotBlank(message = "The seller name cannot be empty") @Size(min = 3, max = 255, message = "The seller name must have between 3 and 255 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "The seller name cannot be empty") @Size(min = 3, max = 255, message = "The seller name must have between 3 and 255 characters") String name) {
        this.name = name;
    }

    public @NotBlank(message = "The address cannot be empty") @Size(min = 3, max = 128, message = "The address must have between 3 and 128 characters") String getAddress() {
        return address;
    }

    public void setAddress(@NotBlank(message = "The address cannot be empty") @Size(min = 3, max = 128, message = "The address must have between 3 and 128 characters") String address) {
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
        UpdateSellerProfileDTO that = (UpdateSellerProfileDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(Inventory, that.Inventory) && Objects.equals(sellerUser, that.sellerUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, Inventory, sellerUser);
    }

    @Override
    public String toString() {
        return "UpdateSellerProfileDTO{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", Inventory=" + Inventory +
                ", sellerUser=" + sellerUser +
                '}';
    }
}
