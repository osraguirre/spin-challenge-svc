package com.spin.controller;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
class TransactionsControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc() {
        return MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void executesTransactionEndToEnd() throws Exception {
        mockMvc().perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "accountId": "acc-123456",
                                    "type": "CREDIT",
                                    "amount": 1500.00,
                                    "currency": "MXN",
                                    "description": "Transferencia recibida"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("EXECUTED"))
                .andExpect(jsonPath("$.providerTransactionId").exists())
                .andExpect(jsonPath("$.accountId").value("acc-123456"));
    }

    @Test
    void rejectsInvalidCurrencyBeforeCallingProvider() throws Exception {
        mockMvc().perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "accountId": "acc-123456",
                                    "type": "CREDIT",
                                    "amount": 1500.00,
                                    "currency": "USD",
                                    "description": "Invalid currency"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("La moneda debe ser MXN."));
    }

    @Test
    void rejectsDebitAmountExceedingLimit() throws Exception {
        mockMvc().perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "accountId": "acc-123456",
                                    "type": "DEBIT",
                                    "amount": 10000.01,
                                    "currency": "MXN",
                                    "description": "Exceeds debit limit"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("El monto de una transaccion DEBIT no puede superar 10000."));
    }

    @Test
    void queriesTransactionsWithFiltersAndPagination() throws Exception {
        mockMvc().perform(post("/api/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "accountId": "acc-query-1",
                            "type": "CREDIT",
                            "amount": 1500.00,
                            "currency": "MXN"
                        }
                        """));

        mockMvc().perform(get("/api/v1/transactions")
                        .param("accountId", "acc-query-1")
                        .param("status", "EXECUTED")
                        .param("type", "CREDIT")
                        .param("page", "0")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$[0].accountId").value("acc-query-1"));
    }
}
