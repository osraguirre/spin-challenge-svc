package com.spin.dto;

import java.math.BigDecimal;

import com.spin.config.SensitiveDataMasker;

public record ProviderTransactionRequest(
        String accountId,
        String type,
        BigDecimal amount,
        String currency) {

        @Override
        public String toString() {
                return "ProviderTransactionRequest[accountId='%s', type='%s', amount=%s, currency='%s']"
                                .formatted(SensitiveDataMasker.maskAccountId(accountId), type, amount, currency);
        }
}