package com.example.demo.author.dto;

import com.example.demo.book.model.Book;
import jakarta.validation.constraints.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class CreateAuthorDTO {

    @NotBlank(message = "The name cannot be empty")
    @Size(min = 3, max = 255, message = "The name must have between 3 and 255 characters")
    private String name;

    @NotNull(message = "A date of birth is required")
    private Date birthDate;

    public CreateAuthorDTO(String name, Date birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public CreateAuthorDTO() {
    }

    public @NotBlank(message = "The name cannot be empty") @Size(min = 3, max = 255, message = "The name must have between 3 and 255 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "The name cannot be empty") @Size(min = 3, max = 255, message = "The name must have between 3 and 255 characters") String name) {
        this.name = name;
    }

    public @NotNull(message = "A date of birth is required") Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(@NotNull(message = "A date of birth is required") Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateAuthorDTO that = (CreateAuthorDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(birthDate, that.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthDate);
    }

    @Override
    public String toString() {
        return "CreateAuthorDTO{" +
                "name='" + name + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
