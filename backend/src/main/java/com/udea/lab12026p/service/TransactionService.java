package com.udea.lab12026p.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udea.lab12026p.dto.TransactionDTO;
import com.udea.lab12026p.dto.TransferRequestDTO;
import com.udea.lab12026p.entity.Customer;
import com.udea.lab12026p.entity.Transaction;
import com.udea.lab12026p.exception.BusinessException;
import com.udea.lab12026p.exception.ResourceNotFoundException;
import com.udea.lab12026p.mapper.TransactionMapper;
import com.udea.lab12026p.repository.CustomerRepository;
import com.udea.lab12026p.repository.TransactionRepository;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final TransactionMapper transactionMapper;

    public TransactionService(TransactionRepository transactionRepository,
                              CustomerRepository customerRepository,
                              TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
        this.transactionMapper = transactionMapper;
    }

    @Transactional
    public TransactionDTO transferMoney(TransferRequestDTO transferRequest) {
        if (transferRequest.getSenderAccountNumber().equals(transferRequest.getReceiverAccountNumber())) {
            throw new BusinessException("Sender and receiver accounts must be different");
        }

        Customer sender = customerRepository.findByAccountNumber(transferRequest.getSenderAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Sender account does not exist"));

        Customer receiver = customerRepository.findByAccountNumber(transferRequest.getReceiverAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Receiver account does not exist"));

        if (sender.getBalance().compareTo(transferRequest.getAmount()) < 0) {
            throw new BusinessException("Insufficient balance in sender account");
        }

        sender.setBalance(sender.getBalance().subtract(transferRequest.getAmount()));
        receiver.setBalance(receiver.getBalance().add(transferRequest.getAmount()));

        customerRepository.save(sender);
        customerRepository.save(receiver);

        Transaction transaction = new Transaction();
        transaction.setSenderAccountNumber(sender.getAccountNumber());
        transaction.setReceiverAccountNumber(receiver.getAccountNumber());
        transaction.setAmount(transferRequest.getAmount());

        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.toDTO(savedTransaction);
    }

    @Transactional(readOnly = true)
    public List<TransactionDTO> getTransactionsForAccount(String accountNumber) {
        if (!customerRepository.existsByAccountNumber(accountNumber)) {
            throw new ResourceNotFoundException("Account does not exist: " + accountNumber);
        }

        List<Transaction> transactions = transactionRepository
                .findBySenderAccountNumberOrReceiverAccountNumberOrderByTimestampDesc(accountNumber, accountNumber);

        return transactions.stream()
                .map(transactionMapper::toDTO)
                .toList();
    }
}