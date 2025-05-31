package com.example.demo.cards.dto;

import com.example.demo.sale.model.Sale;
import com.example.demo.user.model.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Objects;

public class CreateCardDTO {

    @NotNull(message = "The card number can't be empty")
    @Size(min = 16, message = "The card must have at least 16 digits")
    private String cardNumber;
    @NotNull(message = "The bank name can't be null")
    private String bank;
    @NotNull(message = "The CVV (Card Verification Value) can't be null")
    @Size(min = 3, message = "The CVV must have at least 3 digits")
    private String cvv;
    @NotNull(message = "The owner can't be null")
    private User owner;
    private List<Sale> sales;

    public CreateCardDTO(String cardNumber, String bank, String cvv, User owner, List<Sale> sales) {
        this.cardNumber = cardNumber;
        this.bank = bank;
        this.cvv = cvv;
        this.owner = owner;
        this.sales = sales;
    }

    public CreateCardDTO() {
    }

    public @NotNull(message = "The card number can't be empty") @Size(min = 16, message = "The card must have at least 16 digits") String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(@NotNull(message = "The card number can't be empty") @Size(min = 16, message = "The card must have at least 16 digits") String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public @NotNull(message = "The bank name can't be null") String getBank() {
        return bank;
    }

    public void setBank(@NotNull(message = "The bank name can't be null") String bank) {
        this.bank = bank;
    }

    public @NotNull(message = "The CVV (Card Verification Value) can't be null") @Size(min = 3, message = "The CVV must have at least 3 digits") String getCvv() {
        return cvv;
    }

    public void setCvv(@NotNull(message = "The CVV (Card Verification Value) can't be null") @Size(min = 3, message = "The CVV must have at least 3 digits") String cvv) {
        this.cvv = cvv;
    }

    public @NotNull(message = "The owner can't be null") User getOwner() {
        return owner;
    }

    public void setOwner(@NotNull(message = "The owner can't be null") User owner) {
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
        CreateCardDTO that = (CreateCardDTO) o;
        return Objects.equals(cardNumber, that.cardNumber) && Objects.equals(bank, that.bank) && Objects.equals(cvv, that.cvv) && Objects.equals(owner, that.owner) && Objects.equals(sales, that.sales);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, bank, cvv, owner, sales);
    }

    @Override
    public String toString() {
        return "CreateCardDTO{" +
                "cardNumber='" + cardNumber + '\'' +
                ", Bank='" + bank + '\'' +
                ", cvv='" + cvv + '\'' +
                ", owner=" + owner +
                ", sales=" + sales +
                '}';
    }
}
