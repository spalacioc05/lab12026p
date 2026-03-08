package com.udea.lab12026p.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udea.lab12026p.dto.CustomerDTO;
import com.udea.lab12026p.entity.Customer;
import com.udea.lab12026p.exception.BusinessException;
import com.udea.lab12026p.exception.ResourceNotFoundException;
import com.udea.lab12026p.mapper.CustomerMapper;
import com.udea.lab12026p.repository.CustomerRepository;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return customerMapper.toDTO(customer);
    }

    @Transactional(readOnly = true)
    public CustomerDTO getCustomerByAccountNumber(String accountNumber) {
        Customer customer = customerRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with account number: " + accountNumber));
        return customerMapper.toDTO(customer);
    }

    @Transactional
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        if (customerRepository.existsByAccountNumber(customerDTO.getAccountNumber())) {
            throw new BusinessException("Account number already exists: " + customerDTO.getAccountNumber());
        }

        Customer customer = customerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toDTO(savedCustomer);
    }
}
