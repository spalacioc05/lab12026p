package com.udea.lab12026p.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.udea.lab12026p.dto.TransactionDTO;
import com.udea.lab12026p.dto.TransferRequestDTO;
import com.udea.lab12026p.entity.Customer;
import com.udea.lab12026p.entity.Transaction;
import com.udea.lab12026p.exception.BusinessException;
import com.udea.lab12026p.exception.ResourceNotFoundException;
import com.udea.lab12026p.mapper.TransactionMapper;
import com.udea.lab12026p.repository.CustomerRepository;
import com.udea.lab12026p.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private TransactionMapper transactionMapper;

	@InjectMocks
	private TransactionService transactionService;

	@Test
	void transferMoneyShouldRejectSameSenderAndReceiver() {
		TransferRequestDTO request = new TransferRequestDTO();
		request.setSenderAccountNumber("ACC-001");
		request.setReceiverAccountNumber("ACC-001");
		request.setAmount(new BigDecimal("10.00"));

		BusinessException exception = assertThrows(BusinessException.class,
				() -> transactionService.transferMoney(request));

		assertEquals("Sender and receiver accounts must be different", exception.getMessage());
	}

	@Test
	void transferMoneyShouldRejectInsufficientBalance() {
		TransferRequestDTO request = new TransferRequestDTO();
		request.setSenderAccountNumber("ACC-001");
		request.setReceiverAccountNumber("ACC-002");
		request.setAmount(new BigDecimal("200.00"));

		Customer sender = new Customer(1L, "ACC-001", "Ana", "Lopez", new BigDecimal("100.00"));
		Customer receiver = new Customer(2L, "ACC-002", "Luis", "Perez", new BigDecimal("300.00"));

		when(customerRepository.findByAccountNumber("ACC-001")).thenReturn(Optional.of(sender));
		when(customerRepository.findByAccountNumber("ACC-002")).thenReturn(Optional.of(receiver));

		BusinessException exception = assertThrows(BusinessException.class,
				() -> transactionService.transferMoney(request));

		assertEquals("Insufficient balance in sender account", exception.getMessage());
		verify(transactionRepository, never()).save(any(Transaction.class));
	}

	@Test
	void transferMoneyShouldUpdateBalancesAndPersistTransaction() {
		TransferRequestDTO request = new TransferRequestDTO();
		request.setSenderAccountNumber("ACC-001");
		request.setReceiverAccountNumber("ACC-002");
		request.setAmount(new BigDecimal("150.00"));

		Customer sender = new Customer(1L, "ACC-001", "Ana", "Lopez", new BigDecimal("500.00"));
		Customer receiver = new Customer(2L, "ACC-002", "Luis", "Perez", new BigDecimal("300.00"));
		Transaction savedTransaction = new Transaction(7L, "ACC-001", "ACC-002", new BigDecimal("150.00"), LocalDateTime.now());
		TransactionDTO savedDto = new TransactionDTO(7L, "ACC-001", "ACC-002", new BigDecimal("150.00"), savedTransaction.getTimestamp());

		when(customerRepository.findByAccountNumber("ACC-001")).thenReturn(Optional.of(sender));
		when(customerRepository.findByAccountNumber("ACC-002")).thenReturn(Optional.of(receiver));
		when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);
		when(transactionMapper.toDTO(savedTransaction)).thenReturn(savedDto);

		TransactionDTO result = transactionService.transferMoney(request);

		assertSame(savedDto, result);
		assertEquals(new BigDecimal("350.00"), sender.getBalance());
		assertEquals(new BigDecimal("450.00"), receiver.getBalance());
		verify(customerRepository, times(2)).save(any(Customer.class));
		verify(transactionRepository).save(any(Transaction.class));
	}

	@Test
	void getTransactionsForAccountShouldThrowWhenAccountDoesNotExist() {
		when(customerRepository.existsByAccountNumber("ACC-404")).thenReturn(false);

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> transactionService.getTransactionsForAccount("ACC-404"));

		assertEquals("Account does not exist: ACC-404", exception.getMessage());
	}

	@Test
	void getTransactionsForAccountShouldMapRepositoryResults() {
		Transaction firstTransaction = new Transaction(1L, "ACC-001", "ACC-002", new BigDecimal("30.00"), LocalDateTime.now());
		Transaction secondTransaction = new Transaction(2L, "ACC-003", "ACC-001", new BigDecimal("45.00"), LocalDateTime.now().minusDays(1));
		TransactionDTO firstDto = new TransactionDTO(1L, "ACC-001", "ACC-002", new BigDecimal("30.00"), firstTransaction.getTimestamp());
		TransactionDTO secondDto = new TransactionDTO(2L, "ACC-003", "ACC-001", new BigDecimal("45.00"), secondTransaction.getTimestamp());

		when(customerRepository.existsByAccountNumber("ACC-001")).thenReturn(true);
		when(transactionRepository.findBySenderAccountNumberOrReceiverAccountNumberOrderByTimestampDesc("ACC-001", "ACC-001"))
				.thenReturn(List.of(firstTransaction, secondTransaction));
		when(transactionMapper.toDTO(firstTransaction)).thenReturn(firstDto);
		when(transactionMapper.toDTO(secondTransaction)).thenReturn(secondDto);

		List<TransactionDTO> result = transactionService.getTransactionsForAccount("ACC-001");

		assertEquals(2, result.size());
		assertSame(firstDto, result.get(0));
		assertSame(secondDto, result.get(1));
		verify(transactionRepository).findBySenderAccountNumberOrReceiverAccountNumberOrderByTimestampDesc(eq("ACC-001"), eq("ACC-001"));
	}

}