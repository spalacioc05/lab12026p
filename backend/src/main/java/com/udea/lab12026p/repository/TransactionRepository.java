package com.udea.lab12026p.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udea.lab12026p.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySenderAccountNumberOrReceiverAccountNumberOrderByTimestampDesc(String senderAccountNumber, String receiverAccountNumber);
}
