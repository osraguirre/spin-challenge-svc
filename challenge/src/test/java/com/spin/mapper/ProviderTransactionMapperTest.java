package com.spin.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import com.spin.dto.ProviderTransactionRequest;
import com.spin.model.TransactionsModel;
import org.junit.jupiter.api.Test;

class ProviderTransactionMapperTest {

    @Test
    void mapsApiModelToProviderContract() {
        TransactionsModel transaction = mock(TransactionsModel.class);
        when(transaction.getAccountId()).thenReturn("acc-123456");
        when(transaction.getType()).thenReturn("CREDIT");
        when(transaction.getAmount()).thenReturn(new BigDecimal("1500.00"));
        when(transaction.getCurrency()).thenReturn("MXN");

        ProviderTransactionRequest result = new ProviderTransactionMapper().toProviderRequest(transaction);

        assertEquals("acc-123456", result.accountId());
        assertEquals("CREDIT", result.type());
        assertEquals(new BigDecimal("1500.00"), result.amount());
        assertEquals("MXN", result.currency());
    }
}