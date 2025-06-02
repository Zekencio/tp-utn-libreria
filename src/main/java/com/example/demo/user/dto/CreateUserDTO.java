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

    public CreateUserDTO(String name, String password) {
        this.name = name;
        this.password = password;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateUserDTO that = (CreateUserDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password);
    }

    @Override
    public String toString() {
        return "CreateUserDTO{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
