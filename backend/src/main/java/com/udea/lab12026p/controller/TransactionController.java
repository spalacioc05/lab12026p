package com.udea.lab12026p.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udea.lab12026p.dto.TransactionDTO;
import com.udea.lab12026p.dto.TransferRequestDTO;
import com.udea.lab12026p.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/api/transactions", produces = "application/json")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping("/transfer")
    public ResponseEntity<TransactionDTO> transferMoney(@Valid @RequestBody TransferRequestDTO transferRequestDTO) {
        TransactionDTO savedTransaction = transactionService.transferMoney(transferRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(transactionService.getTransactionsForAccount(accountNumber));
    }
}