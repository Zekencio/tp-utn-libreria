package com.example.demo.book;

import com.example.demo.author.Author;
import com.example.demo.genre.Genre;
import com.example.demo.sale.Sale;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String description;
    private Double price;
    private Long stock;

    @ManyToOne
    private Author author;

    @ManyToMany(mappedBy = "genres")
    private Set<Genre> genres;

    @ManyToMany(mappedBy = "sales")
    private List<Sale> sales;


}
