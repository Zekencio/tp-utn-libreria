package com.example.demo.cards.model;

import com.example.demo.sale.model.Sale;
import com.example.demo.user.model.User;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String cardNumber;
    @Column(nullable = false)
    private String bank;
    @Column(nullable = false)
    private String cvv;

    @ManyToOne
    private User owner;

    @OneToMany
    private List<Sale> sales;

    public Card(Long id, String cardNumber, String bank, String cvv, User owner, List<Sale> sales) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.bank = bank;
        this.cvv = cvv;
        this.owner = owner;
        this.sales = sales;
    }

    public Card(String cardNumber, String bank, String cvv, User owner, List<Sale> sales) {
        this.cardNumber = cardNumber;
        this.bank = bank;
        this.cvv = cvv;
        this.owner = owner;
        this.sales = sales;
    }

    public Card() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id) && Objects.equals(cardNumber, card.cardNumber) && Objects.equals(bank, card.bank) && Objects.equals(cvv, card.cvv) && Objects.equals(owner, card.owner) && Objects.equals(sales, card.sales);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardNumber, bank, cvv, owner, sales);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", Bank='" + bank + '\'' +
                ", cvv='" + cvv + '\'' +
                ", owner=" + owner +
                ", sales=" + sales +
                '}';
    }
}
