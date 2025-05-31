package com.example.demo.author.dto;

import com.example.demo.book.model.Book;
import jakarta.validation.constraints.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class UpdateAuthorDTO {

    @Size(min = 3, max = 255, message = "The new name must have between 3 and 255 characters")
    private String name;

    @NotNull(message = "The date of birth can't be null")
    private Date birthDate;

    private List<Book> bookslist;

    public UpdateAuthorDTO(String name, Date birthDate, List<Book> bookslist) {
        this.name = name;
        this.birthDate = birthDate;
        this.bookslist = bookslist;
    }

    public UpdateAuthorDTO() {
    }

    public @Size(min = 3, max = 255, message = "The new name must have between 3 and 255 characters") String getName() {
        return name;
    }

    public void setName(@Size(min = 3, max = 255, message = "The new name must have between 3 and 255 characters") String name) {
        this.name = name;
    }

    public @NotNull(message = "The date of birth can't be null") Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(@NotNull(message = "The date of birth can't be null") Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<Book> getBookslist() {
        return bookslist;
    }

    public void setBookslist(List<Book> bookslist) {
        this.bookslist = bookslist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateAuthorDTO that = (UpdateAuthorDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(birthDate, that.birthDate) && Objects.equals(bookslist, that.bookslist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthDate, bookslist);
    }

    @Override
    public String toString() {
        return "UpdateAuthorDTO{" +
                "name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", bookslist=" + bookslist +
                '}';
    }
}
