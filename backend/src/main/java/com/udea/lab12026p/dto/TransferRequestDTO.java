package com.udea.lab12026p.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TransferRequestDTO {
    @NotBlank(message = "senderAccountNumber is required")
    private String senderAccountNumber;
    @NotBlank(message = "receiverAccountNumber is required")
    private String receiverAccountNumber;
    @NotNull(message = "amount is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "amount must be greater than 0")
    private BigDecimal amount;

    public TransferRequestDTO() {
    }

    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public void setSenderAccountNumber(String senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(String receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
