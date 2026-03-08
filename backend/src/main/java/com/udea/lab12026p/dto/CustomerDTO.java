package com.udea.lab12026p.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CustomerDTO {

   private Long id;
   @NotBlank(message = "firstName is required")
   private String firstName;
   @NotBlank(message = "lastName is required")
   private String lastName;
    @NotBlank(message = "accountNumber is required")
    private String accountNumber;
    @NotNull(message = "balance is required")
    @DecimalMin(value = "0.00", inclusive = true, message = "balance must be greater than or equal to 0")
    private BigDecimal balance;

    public CustomerDTO() {}

    public CustomerDTO(Long id, String firstName, String lastName, String accountNumber, BigDecimal balance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
