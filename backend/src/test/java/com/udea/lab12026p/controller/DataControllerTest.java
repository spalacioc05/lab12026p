package com.udea.lab12026p.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DataController.class)
class DataControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void healthEndpointShouldReturnExpectedMessage() throws Exception {
		mockMvc.perform(get("/api/faker/health"))
				.andExpect(status().isOk())
				.andExpect(content().string("Banking API - HEALTH CHECK OK!"));
	}

	@Test
	void versionEndpointShouldReturnExpectedMessage() throws Exception {
		mockMvc.perform(get("/api/faker/version"))
				.andExpect(status().isOk())
				.andExpect(content().string("Banking API - version 1.0.0"));
	}

	@Test
	void customersEndpointShouldReturnTenGeneratedCustomers() throws Exception {
		mockMvc.perform(get("/api/faker/customers"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(10)))
				.andExpect(jsonPath("$[0].firstName").isString())
				.andExpect(jsonPath("$[0].lastName").isString())
				.andExpect(jsonPath("$[0].accountNumber").isString())
				.andExpect(jsonPath("$[0].balance").isNumber());
	}

	@Test
	void transactionsEndpointShouldReturnTenGeneratedTransactions() throws Exception {
		mockMvc.perform(get("/api/faker/transactions"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(10)))
				.andExpect(jsonPath("$[0].senderAccount").isString())
				.andExpect(jsonPath("$[0].receiverAccount").isString())
				.andExpect(jsonPath("$[0].amount").isNumber())
				.andExpect(jsonPath("$[0].timestamp").isString());
	}

	@Test
	void cardsEndpointShouldReturnTenGeneratedCards() throws Exception {
		mockMvc.perform(get("/api/faker/cards"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(10)))
				.andExpect(jsonPath("$[0].cardType").isString())
				.andExpect(jsonPath("$[0].cardNumber").isString())
				.andExpect(jsonPath("$[0].expiry").isString())
				.andExpect(jsonPath("$[0].iban").isString());
	}

	@Test
	void currenciesEndpointShouldReturnTenGeneratedCurrencies() throws Exception {
		mockMvc.perform(get("/api/faker/currencies"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(10)))
				.andExpect(jsonPath("$[0].name").isString())
				.andExpect(jsonPath("$[0].code").isString());
	}

}