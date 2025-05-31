package com.example.demo.cards.dto;

import com.example.demo.sale.model.Sale;
import com.example.demo.user.model.User;
import java.util.List;
import java.util.Objects;

public class CardDTO {

    private Long id;
    private String cardNumber;
    private String Bank;
    private String cvv;
    private User owner;
    private List<Sale> sales;

    public CardDTO(Long id, String cardNumber, String bank, String cvv, User owner, List<Sale> sales) {
        this.id = id;
        this.cardNumber = cardNumber;
        Bank = bank;
        this.cvv = cvv;
        this.owner = owner;
        this.sales = sales;
    }

    public CardDTO(String cardNumber, String bank, String cvv, User owner, List<Sale> sales) {
        this.cardNumber = cardNumber;
        Bank = bank;
        this.cvv = cvv;
        this.owner = owner;
        this.sales = sales;
    }

    public CardDTO() {
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
        return Bank;
    }

    public void setBank(String bank) {
        Bank = bank;
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
        CardDTO cardDTO = (CardDTO) o;
        return Objects.equals(id, cardDTO.id) && Objects.equals(cardNumber, cardDTO.cardNumber) && Objects.equals(Bank, cardDTO.Bank)
                && Objects.equals(cvv, cardDTO.cvv) && Objects.equals(owner, cardDTO.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardNumber, Bank, cvv, owner);
    }

    @Override
    public String toString() {
        return "CardDTO{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", Bank='" + Bank + '\'' +
                ", owner=" + owner +
                '}';
    }
}
