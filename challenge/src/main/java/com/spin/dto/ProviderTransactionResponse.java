package com.spin.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record ProviderTransactionResponse(
        String transactionId,
        String status,
        BigDecimal balance,
        Instant executedAt,
        String code,
        String message) {

        @Override
        public String toString() {
                return "ProviderTransactionResponse[transactionId='%s', status='%s', balance=%s, executedAt=%s, code='%s', message='%s']"
                                .formatted(transactionId, status, balance, executedAt, code, message);
        }
}