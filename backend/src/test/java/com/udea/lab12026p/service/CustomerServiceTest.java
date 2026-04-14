package com.udea.lab12026p.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.udea.lab12026p.dto.CustomerDTO;
import com.udea.lab12026p.entity.Customer;
import com.udea.lab12026p.exception.BusinessException;
import com.udea.lab12026p.exception.ResourceNotFoundException;
import com.udea.lab12026p.mapper.CustomerMapper;
import com.udea.lab12026p.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private CustomerMapper customerMapper;

	@InjectMocks
	private CustomerService customerService;

	@Test
	void getAllCustomersShouldMapRepositoryResults() {
		Customer firstCustomer = new Customer(1L, "ACC-001", "Ana", "Lopez", new BigDecimal("1000.00"));
		Customer secondCustomer = new Customer(2L, "ACC-002", "Luis", "Perez", new BigDecimal("2500.00"));
		CustomerDTO firstDto = new CustomerDTO(1L, "Ana", "Lopez", "ACC-001", new BigDecimal("1000.00"));
		CustomerDTO secondDto = new CustomerDTO(2L, "Luis", "Perez", "ACC-002", new BigDecimal("2500.00"));

		when(customerRepository.findAll()).thenReturn(List.of(firstCustomer, secondCustomer));
		when(customerMapper.toDTO(firstCustomer)).thenReturn(firstDto);
		when(customerMapper.toDTO(secondCustomer)).thenReturn(secondDto);

		List<CustomerDTO> result = customerService.getAllCustomers();

		assertEquals(2, result.size());
		assertSame(firstDto, result.get(0));
		assertSame(secondDto, result.get(1));
	}

	@Test
	void getCustomerByIdShouldThrowWhenCustomerDoesNotExist() {
		when(customerRepository.findById(99L)).thenReturn(Optional.empty());

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> customerService.getCustomerById(99L));

		assertEquals("Customer not found with id: 99", exception.getMessage());
	}

	@Test
	void createCustomerShouldRejectDuplicatedAccountNumber() {
		CustomerDTO customerDTO = new CustomerDTO(null, "Ana", "Lopez", "ACC-001", new BigDecimal("1000.00"));
		when(customerRepository.existsByAccountNumber("ACC-001")).thenReturn(true);

		BusinessException exception = assertThrows(BusinessException.class,
				() -> customerService.createCustomer(customerDTO));

		assertEquals("Account number already exists: ACC-001", exception.getMessage());
		verify(customerRepository, never()).save(any(Customer.class));
	}

	@Test
	void createCustomerShouldPersistAndReturnMappedDto() {
		CustomerDTO inputDto = new CustomerDTO(null, "Ana", "Lopez", "ACC-001", new BigDecimal("1000.00"));
		Customer entity = new Customer(null, "ACC-001", "Ana", "Lopez", new BigDecimal("1000.00"));
		Customer savedEntity = new Customer(10L, "ACC-001", "Ana", "Lopez", new BigDecimal("1000.00"));
		CustomerDTO savedDto = new CustomerDTO(10L, "Ana", "Lopez", "ACC-001", new BigDecimal("1000.00"));

		when(customerRepository.existsByAccountNumber("ACC-001")).thenReturn(false);
		when(customerMapper.toEntity(inputDto)).thenReturn(entity);
		when(customerRepository.save(entity)).thenReturn(savedEntity);
		when(customerMapper.toDTO(savedEntity)).thenReturn(savedDto);

		CustomerDTO result = customerService.createCustomer(inputDto);

		assertSame(savedDto, result);
		verify(customerRepository).save(entity);
	}

}