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

    public UserDTO(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public UserDTO(String name, String password) {
        this.name = name;
        this.password = password;
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
                '}';
    }
}
