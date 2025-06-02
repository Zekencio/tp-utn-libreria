package com.example.demo.cards.dto;

import com.example.demo.sale.model.Sale;
import com.example.demo.user.model.User;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Objects;

public class UpdateCardDTO {

    @Size(min = 16, message = "The card must have at least 16 digits")
    private String cardNumber;
    @NotNull(message = "The bank name can't be null")
    private String bank;
    @Size(min = 3, message = "The CVV must have at least 3 digits")
    private String cvv;
    @NotNull(message = "The owner can't be null")
    private User owner;

    public UpdateCardDTO(String cardNumber, String bank, String cvv, User owner) {
        this.cardNumber = cardNumber;
        this.bank = bank;
        this.cvv = cvv;
        this.owner = owner;
    }

    public UpdateCardDTO() {
    }

    public @Size(min = 16, message = "The card must have at least 16 digits") String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(@Size(min = 16, message = "The card must have at least 16 digits") String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public @NotNull(message = "The bank name can't be null") String getBank() {
        return bank;
    }

    public void setBank(@NotNull(message = "The bank name can't be null") String bank) {
        this.bank = bank;
    }

    public @Size(min = 3, message = "The CVV must have at least 3 digits") String getCvv() {
        return cvv;
    }

    public void setCvv(@Size(min = 3, message = "The CVV must have at least 3 digits") String cvv) {
        this.cvv = cvv;
    }

    public @NotNull(message = "The owner can't be null") User getOwner() {
        return owner;
    }

    public void setOwner(@NotNull(message = "The owner can't be null") User owner) {
        this.owner = owner;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateCardDTO that = (UpdateCardDTO) o;
        return Objects.equals(cardNumber, that.cardNumber) && Objects.equals(bank, that.bank) && Objects.equals(cvv, that.cvv) && Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, bank, cvv, owner);
    }

    @Override
    public String toString() {
        return "UpdateCardDTO{" +
                "cardNumber='" + cardNumber + '\'' +
                ", bank='" + bank + '\'' +
                ", cvv='" + cvv + '\'' +
                ", owner=" + owner +
                '}';
    }
}
