package com.example.demo.author;

import com.example.demo.book.Book;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private Date birthDate;

    @OneToMany
    private List<Book> bookslist;

}
