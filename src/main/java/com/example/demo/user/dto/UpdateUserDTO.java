package com.example.demo.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Objects;

public class UpdateUserDTO {

    @Size(min = 3, max = 255, message = "The username must have between 3 and 255 characters")
    private String name;
    @Size(min = 8, message = "The password must be at least 8 characters long")
    private String password;
    @Size(min = 8, message = "The current password must be at least 8 characters long")
    private String currentPassword;

    public UpdateUserDTO(String name, String password, String currentPassword) {
        this.name = name;
        this.password = password;
        this.currentPassword = currentPassword;
    }

    public UpdateUserDTO() {
    }

    public @Size(min = 3, max = 255, message = "The username must have between 3 and 255 characters") String getName() {
        return name;
    }

    public void setName(@Size(min = 3, max = 255, message = "The username must have between 3 and 255 characters") String name) {
        this.name = name;
    }

    public @Size(min = 8, message = "The password must be at least 8 characters long") String getPassword() {
        return password;
    }

    public void setPassword(@Size(min = 8, message = "The password must be at least 8 characters long") String password) {
        this.password = password;
    }

    public @Size(min = 8, message = "The current password must be at least 8 characters long") String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(@Size(min = 8, message = "The current password must be at least 8 characters long") String currentPassword) {
        this.currentPassword = currentPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateUserDTO that = (UpdateUserDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, currentPassword);
    }

    @Override
    public String toString() {
    return "UpdateUserDTO{" +
        "name='" + name + '\'' +
        ", password='" + password + '\'' +
        ", currentPassword='" + currentPassword + '\'' +
        '}';
    }
}
