package com.udea.lab12026p.controller;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udea.lab12026p.dto.TransactionDTO;
import com.udea.lab12026p.dto.TransferRequestDTO;
import com.udea.lab12026p.exception.BusinessException;
import com.udea.lab12026p.exception.GlobalExceptionHandler;
import com.udea.lab12026p.service.TransactionService;

@WebMvcTest(TransactionController.class)
@Import(GlobalExceptionHandler.class)
class TransactionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private TransactionService transactionService;

	@Test
	void transferMoneyShouldReturnBadRequestWhenPayloadIsInvalid() throws Exception {
		TransferRequestDTO payload = new TransferRequestDTO();

		mockMvc.perform(post("/api/transactions/transfer")
						.contentType(APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(payload)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Validation failed"))
				.andExpect(jsonPath("$.validations.senderAccountNumber").exists())
				.andExpect(jsonPath("$.validations.receiverAccountNumber").exists())
				.andExpect(jsonPath("$.validations.amount").exists());
	}

	@Test
	void transferMoneyShouldReturnBadRequestWhenServiceRejectsBusinessRule() throws Exception {
		TransferRequestDTO payload = new TransferRequestDTO();
		payload.setSenderAccountNumber("ACC-001");
		payload.setReceiverAccountNumber("ACC-001");
		payload.setAmount(new BigDecimal("50.00"));

		when(transactionService.transferMoney(org.mockito.ArgumentMatchers.any(TransferRequestDTO.class)))
				.thenThrow(new BusinessException("Sender and receiver accounts must be different"));

		mockMvc.perform(post("/api/transactions/transfer")
						.contentType(APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(payload)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Sender and receiver accounts must be different"));
	}

	@Test
	void transferMoneyShouldReturnCreatedTransaction() throws Exception {
		TransferRequestDTO payload = new TransferRequestDTO();
		payload.setSenderAccountNumber("ACC-001");
		payload.setReceiverAccountNumber("ACC-002");
		payload.setAmount(new BigDecimal("50.00"));

		TransactionDTO response = new TransactionDTO(9L, "ACC-001", "ACC-002", new BigDecimal("50.00"), LocalDateTime.of(2026, 4, 13, 10, 0));

		when(transactionService.transferMoney(org.mockito.ArgumentMatchers.any(TransferRequestDTO.class))).thenReturn(response);

		mockMvc.perform(post("/api/transactions/transfer")
						.contentType(APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(payload)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(9))
				.andExpect(jsonPath("$.senderAccountNumber").value("ACC-001"))
				.andExpect(jsonPath("$.receiverAccountNumber").value("ACC-002"))
				.andExpect(jsonPath("$.amount").value(50.00));
	}

	@Test
	void getTransactionsByAccountShouldReturnServiceResults() throws Exception {
		TransactionDTO transaction = new TransactionDTO(5L, "ACC-001", "ACC-002", new BigDecimal("20.00"), LocalDateTime.of(2026, 4, 13, 9, 0));

		when(transactionService.getTransactionsForAccount("ACC-001")).thenReturn(List.of(transaction));

		mockMvc.perform(get("/api/transactions/account/ACC-001"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(5))
				.andExpect(jsonPath("$[0].senderAccountNumber").value("ACC-001"));
	}

}