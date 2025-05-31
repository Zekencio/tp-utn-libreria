package com.example.demo.genre.dto;

import com.example.demo.book.model.Book;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.Set;

public class UpdateGenreDTO {

    @NotNull(message = "The gender name can't be null")
    private String name;
    private String description;
    private Set<Book> books;

    public UpdateGenreDTO(String name, String description, Set<Book> books) {
        this.name = name;
        this.description = description;
        this.books = books;
    }

    public UpdateGenreDTO() {
    }

    public @NotNull(message = "The gender name can't be null") String getName() {
        return name;
    }

    public void setName(@NotNull(message = "The gender name can't be null") String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateGenreDTO that = (UpdateGenreDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(books, that.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, books);
    }

    @Override
    public String toString() {
        return "UpdateGenreDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", books=" + books +
                '}';
    }
}
