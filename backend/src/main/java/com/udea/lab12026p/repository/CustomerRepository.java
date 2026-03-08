package com.udea.lab12026p.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udea.lab12026p.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByAccountNumber(String accountNumber);
    boolean existsByAccountNumber(String accountNumber);
}
