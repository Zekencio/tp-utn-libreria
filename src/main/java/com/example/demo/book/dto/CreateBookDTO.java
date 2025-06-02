package com.example.demo.book.dto;

import com.example.demo.author.model.Author;
import com.example.demo.genre.model.Genre;
import com.example.demo.sale.model.Sale;
import com.example.demo.sellerprofile.model.SellerProfile;
import com.example.demo.user.model.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CreateBookDTO {

    @NotNull(message = "The name can't be null")
    @Size(min = 1, max = 255, message = "The name of the book must have between 1 and 255 characters")
    private String name;

    private String description;

    @NotNull(message = "The price can't be null")
    @Min(value = 0, message = "The price cannot be a negative value")
    private Double price;

    @NotNull(message = "The stock can't be null")
    @Min(value = 0, message = "The stock cannot be a negative value")
    private Long stock;

    @NotNull(message = "The author can't be null")
    private Author author;

    private Set<Genre> genres;
    private SellerProfile seller;

    public CreateBookDTO(String name, String description, Double price, Long stock, Author author, Set<Genre> genres, SellerProfile seller) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.author = author;
        this.genres = genres;
        this.seller = seller;
    }

    public CreateBookDTO() {
    }

    public @NotNull(message = "The name can't be null") @Size(min = 1, max = 255, message = "The name of the book must have between 1 and 255 characters") String getName() {
        return name;
    }

    public void setName(@NotNull(message = "The name can't be null") @Size(min = 1, max = 255, message = "The name of the book must have between 1 and 255 characters") String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public @NotNull(message = "The price can't be null") @Min(value = 0, message = "The price cannot be a negative value") Double getPrice() {
        return price;
    }

    public void setPrice(@NotNull(message = "The price can't be null") @Min(value = 0, message = "The price cannot be a negative value") Double price) {
        this.price = price;
    }

    public @NotNull(message = "The stock can't be null") @Min(value = 0, message = "The stock cannot be a negative value") Long getStock() {
        return stock;
    }

    public void setStock(@NotNull(message = "The stock can't be null") @Min(value = 0, message = "The stock cannot be a negative value") Long stock) {
        this.stock = stock;
    }

    public @NotNull(message = "The author can't be null") Author getAuthor() {
        return author;
    }

    public void setAuthor(@NotNull(message = "The author can't be null") Author author) {
        this.author = author;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }


    public SellerProfile getSeller() {
        return seller;
    }

    public void setSeller(SellerProfile seller) {
        this.seller = seller;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateBookDTO that = (CreateBookDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(price, that.price)
                && Objects.equals(stock, that.stock) && Objects.equals(author, that.author) && Objects.equals(genres, that.genres) && Objects.equals(seller, that.seller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, stock, author, genres,seller);
    }

    @Override
    public String toString() {
        return "CreateBookDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", author=" + author +
                ", genres=" + genres +
                ", seller=" + seller +
                '}';
    }
}
