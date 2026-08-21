package com.spin.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import com.spin.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void findsTransactionsUsingOptionalFiltersAndPagination() {
        transactionRepository.saveAll(List.of(
                transaction("id-1", "acc-1", "CREDIT", "EXECUTED", 3),
                transaction("id-2", "acc-1", "DEBIT", "REJECTED", 2),
                transaction("id-3", "acc-2", "CREDIT", "EXECUTED", 1)));

        Page<Transaction> result = transactionRepository.findAll(
                TransactionSpecifications.withFilters("acc-1", "EXECUTED", "CREDIT"),
                PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
        assertEquals("id-1", result.getContent().getFirst().getId());
    }

    @Test
    void findsAllTransactionsWhenNoFiltersAreProvided() {
        transactionRepository.saveAll(List.of(
                transaction("id-4", "acc-3", "CREDIT", "EXECUTED", 1),
                transaction("id-5", "acc-4", "DEBIT", "REJECTED", 2)));

        Page<Transaction> result = transactionRepository.findAll(
                TransactionSpecifications.withFilters(null, null, null),
                PageRequest.of(0, 10));

        assertEquals(2, result.getTotalElements());
    }

    private Transaction transaction(String id, String accountId, String type, String status, int secondsAgo) {
        return Transaction.builder()
                .id(id)
                .accountId(accountId)
                .type(type)
                .amount(new BigDecimal("1500.00"))
                .currency("MXN")
                .description("test")
                .status(status)
                .providerTransactionId("txn-" + id)
                .balanceAfter(new BigDecimal("5500.00"))
                .createdAt(Instant.now().minusSeconds(secondsAgo))
                .build();
    }
}