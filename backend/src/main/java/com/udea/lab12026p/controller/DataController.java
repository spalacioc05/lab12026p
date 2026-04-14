package com.udea.lab12026p.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/api/faker")
public class DataController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Health check del servicio bancario.
     */
    @GetMapping("/health")
    public String healthCheck() {
        return "Banking API - HEALTH CHECK OK!";
    }

    /**
     * Versión actual de la aplicación.
     */
    @GetMapping("/version")
    public String version() {
        return "Banking API - version 1.0.0";
    }

    /**
     * Genera 10 clientes ficticios con nombre, apellido, número de cuenta y saldo.
     */
    @GetMapping("/customers")
    public JsonNode getFakeCustomers() {
        var faker = new Faker(new Locale("en-US"));
        var customers = objectMapper.createArrayNode();
        for (int i = 0; i < 10; i++) {
            var finance = faker.finance();
            customers.add(objectMapper.createObjectNode()
                    .put("firstName", faker.name().firstName())
                    .put("lastName", faker.name().lastName())
                    .put("accountNumber", finance.iban())
                    .put("balance", faker.number().randomDouble(2, 100, 100000)));
        }
        return customers;
    }

    /**
     * Genera 10 transacciones ficticias entre cuentas con monto y fecha.
     */
    @GetMapping("/transactions")
    public JsonNode getFakeTransactions() {
        var faker = new Faker(new Locale("en-US"));
        var transactions = objectMapper.createArrayNode();
        for (int i = 0; i < 10; i++) {
            transactions.add(objectMapper.createObjectNode()
                    .put("senderAccount", faker.finance().iban())
                    .put("receiverAccount", faker.finance().iban())
                    .put("amount", faker.number().randomDouble(2, 10, 5000))
                    .put("timestamp", faker.date().past(30, java.util.concurrent.TimeUnit.DAYS).toString()));
        }
        return transactions;
    }

    /**
     * Genera 10 tarjetas de crédito ficticias (tipo, número enmascarado, expiración).
     */
    @GetMapping("/cards")
    public JsonNode getFakeCards() {
        var faker = new Faker(new Locale("en-US"));
        var cards = objectMapper.createArrayNode();
        for (int i = 0; i < 10; i++) {
            var finance = faker.finance();
            var creditCard = faker.business();
            cards.add(objectMapper.createObjectNode()
                    .put("cardType", creditCard.creditCardType())
                    .put("cardNumber", creditCard.creditCardNumber())
                    .put("expiry", creditCard.creditCardExpiry())
                    .put("iban", finance.iban()));
        }
        return cards;
    }

    /**
     * Genera 10 monedas ficticias con nombre y código.
     */
    @GetMapping("/currencies")
    public JsonNode getFakeCurrencies() {
        var faker = new Faker(new Locale("en-US"));
        var currencies = objectMapper.createArrayNode();
        for (int i = 0; i < 10; i++) {
            var currency = faker.currency();
            currencies.add(objectMapper.createObjectNode()
                    .put("name", currency.name())
                    .put("code", currency.code()));
        }
        return currencies;
    }
}
