package com.spin.client;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.spin.dto.ProviderTransactionRequest;
import com.spin.dto.ProviderTransactionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MockProviderClient implements ProviderClient {
    private final String outcome;

    public MockProviderClient(@Value("${provider.mock.outcome:APPROVED}") String outcome) {
        this.outcome = outcome;
    }

    @Override
    public ProviderTransactionResponse execute(ProviderTransactionRequest request) {
        if ("REJECTED".equalsIgnoreCase(outcome)) {
            ProviderTransactionResponse response = new ProviderTransactionResponse(
                    null,
                    "REJECTED",
                    null,
                    Instant.now(),
                    "INSUFFICIENT_FUNDS",
                    "The account does not have enough balance to complete the transaction");
            log.warn("Provider response: {\"status\":\"{}\",\"code\":\"{}\",\"message\":\"{}\"}",
                response.status(), response.code(), response.message());
            return response;
        }

        ProviderTransactionResponse response = new ProviderTransactionResponse(
                "txn-" + UUID.randomUUID(),
                "APPROVED",
                new BigDecimal("5500.00"),
                Instant.now(),
                null,
                null);
        log.info("Provider response: {\"transactionId\":\"{}\",\"status\":\"{}\",\"balance\":{},\"executedAt\":\"{}\"}",
            response.transactionId(), response.status(), response.balance(), response.executedAt());
        return response;
    }
}