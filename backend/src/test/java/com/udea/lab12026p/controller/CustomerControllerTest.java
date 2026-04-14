package com.udea.lab12026p.controller;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udea.lab12026p.dto.CustomerDTO;
import com.udea.lab12026p.exception.GlobalExceptionHandler;
import com.udea.lab12026p.exception.ResourceNotFoundException;
import com.udea.lab12026p.service.CustomerService;

@WebMvcTest(CustomerController.class)
@Import(GlobalExceptionHandler.class)
class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CustomerService customerService;

	@Test
	void getAllCustomersShouldReturnServiceResults() throws Exception {
		CustomerDTO firstCustomer = new CustomerDTO(1L, "Ana", "Lopez", "ACC-001", new BigDecimal("1000.00"));
		CustomerDTO secondCustomer = new CustomerDTO(2L, "Luis", "Perez", "ACC-002", new BigDecimal("2500.00"));

		when(customerService.getAllCustomers()).thenReturn(List.of(firstCustomer, secondCustomer));

		mockMvc.perform(get("/api/customers"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].accountNumber").value("ACC-001"))
				.andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].accountNumber").value("ACC-002"));
	}

	@Test
	void createCustomerShouldReturnBadRequestWhenPayloadIsInvalid() throws Exception {
		CustomerDTO payload = new CustomerDTO(null, "", "", "", null);

		mockMvc.perform(post("/api/customers")
						.contentType(APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(payload)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Validation failed"))
				.andExpect(jsonPath("$.validations.firstName").exists())
				.andExpect(jsonPath("$.validations.lastName").exists())
				.andExpect(jsonPath("$.validations.accountNumber").exists())
				.andExpect(jsonPath("$.validations.balance").exists());
	}

	@Test
	void getCustomerByIdShouldReturnNotFoundWhenServiceThrows() throws Exception {
		when(customerService.getCustomerById(42L))
				.thenThrow(new ResourceNotFoundException("Customer not found with id: 42"));

		mockMvc.perform(get("/api/customers/42"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Customer not found with id: 42"));
	}

	@Test
	void getCustomerByIdShouldReturnServiceResult() throws Exception {
		CustomerDTO customer = new CustomerDTO(42L, "Ana", "Lopez", "ACC-042", new BigDecimal("1500.00"));

		when(customerService.getCustomerById(42L)).thenReturn(customer);

		mockMvc.perform(get("/api/customers/42"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(42))
				.andExpect(jsonPath("$.accountNumber").value("ACC-042"))
				.andExpect(jsonPath("$.firstName").value("Ana"));
	}

	@Test
	void getCustomerByAccountNumberShouldReturnServiceResult() throws Exception {
		CustomerDTO customer = new CustomerDTO(3L, "Laura", "Gomez", "ACC-003", new BigDecimal("900.00"));

		when(customerService.getCustomerByAccountNumber("ACC-003")).thenReturn(customer);

		mockMvc.perform(get("/api/customers/account/ACC-003"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(3))
				.andExpect(jsonPath("$.accountNumber").value("ACC-003"))
				.andExpect(jsonPath("$.firstName").value("Laura"));
	}

	@Test
	void createCustomerShouldReturnCreatedCustomer() throws Exception {
		CustomerDTO input = new CustomerDTO(null, "Ana", "Lopez", "ACC-001", new BigDecimal("1000.00"));
		CustomerDTO saved = new CustomerDTO(1L, "Ana", "Lopez", "ACC-001", new BigDecimal("1000.00"));

		when(customerService.createCustomer(org.mockito.ArgumentMatchers.any(CustomerDTO.class))).thenReturn(saved);

		mockMvc.perform(post("/api/customers")
						.contentType(APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.accountNumber").value("ACC-001"));
	}

}