package com.spin.mapper;

import com.spin.dto.ProviderTransactionRequest;
import com.spin.dto.TransactionResponse;
import com.spin.entity.Transaction;
import com.spin.model.TransactionsModel;
import com.spin.dto.ProviderTransactionResponse;
import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ProviderTransactionMapper {

    public ProviderTransactionRequest toProviderRequest(TransactionsModel transaction) {
        return new ProviderTransactionRequest(
                transaction.getAccountId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getCurrency());
    }

    public TransactionResponse toTransactionResponse(
            TransactionsModel transaction,
            ProviderTransactionResponse providerResponse,
            UUID id,
            Instant createdAt) {
        return new TransactionResponse(
                id.toString(),
                transaction.getAccountId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getDescription(),
                "APPROVED".equals(providerResponse.status()) ? "EXECUTED" : providerResponse.status(),
                providerResponse.transactionId(),
                providerResponse.balance(),
                createdAt);
    }

    public Transaction toEntity(TransactionResponse response) {
        return Transaction.builder()
            .id(response.id())
            .accountId(response.accountId())
            .type(response.type())
            .amount(response.amount())
            .currency(response.currency())
            .description(response.description())
            .status(response.status())
            .providerTransactionId(response.providerTransactionId())
            .balanceAfter(response.balanceAfter())
            .createdAt(response.createdAt())
            .build();
    }

    public TransactionResponse toTransactionResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getAccountId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getDescription(),
                transaction.getStatus(),
                transaction.getProviderTransactionId(),
                transaction.getBalanceAfter(),
                transaction.getCreatedAt());
    }
}