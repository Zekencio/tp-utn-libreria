package com.example.demo.user;

import com.example.demo.sale.Sale;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;


    @OneToMany
    private List<Sale> sales;
}
