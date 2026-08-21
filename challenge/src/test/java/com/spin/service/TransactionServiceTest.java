package com.spin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.spin.client.ProviderClient;
import com.spin.dto.ProviderTransactionRequest;
import com.spin.dto.ProviderTransactionResponse;
import com.spin.dto.TransactionResponse;
import com.spin.entity.Transaction;
import com.spin.mapper.ProviderTransactionMapper;
import com.spin.model.TransactionsModel;
import com.spin.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private ProviderClient providerClient;

    @Mock
    private ProviderTransactionMapper transactionMapper;

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    void executeTransactionPersistsApprovedProviderResult() {
        TransactionsModel request = new TransactionsModel();
        ProviderTransactionRequest providerRequest = new ProviderTransactionRequest(
                "acc-123456", "CREDIT", new BigDecimal("1500.00"), "MXN");
        ProviderTransactionResponse providerResponse = new ProviderTransactionResponse(
                "txn-789", "APPROVED", new BigDecimal("5500.00"), Instant.now(), null, null);
        TransactionResponse response = response("EXECUTED", "txn-789");
        Transaction entity = Transaction.builder().id(response.id()).build();

        when(transactionMapper.toProviderRequest(request)).thenReturn(providerRequest);
        when(providerClient.execute(providerRequest)).thenReturn(providerResponse);
        when(transactionMapper.toTransactionResponse(
                any(TransactionsModel.class), any(ProviderTransactionResponse.class),
                any(UUID.class), any(Instant.class))).thenReturn(response);
        when(transactionMapper.toEntity(response)).thenReturn(entity);
        when(transactionRepository.save(entity)).thenReturn(entity);
        when(transactionMapper.toTransactionResponse(entity)).thenReturn(response);

        TransactionService service = new TransactionService(providerClient, transactionMapper, transactionRepository);

        assertEquals(response, service.executeTransaction(request));
        verify(transactionRepository).save(entity);
    }

    @Test
    void executeTransactionPersistsRejectedProviderResult() {
        TransactionsModel request = new TransactionsModel();
        ProviderTransactionRequest providerRequest = new ProviderTransactionRequest(
                "acc-123456", "DEBIT", new BigDecimal("1500.00"), "MXN");
        ProviderTransactionResponse providerResponse = new ProviderTransactionResponse(
                null, "REJECTED", null, Instant.now(), "INSUFFICIENT_FUNDS", "Insufficient funds");
        TransactionResponse response = response("REJECTED", null);
        Transaction entity = Transaction.builder().id(response.id()).build();

        when(transactionMapper.toProviderRequest(request)).thenReturn(providerRequest);
        when(providerClient.execute(providerRequest)).thenReturn(providerResponse);
        when(transactionMapper.toTransactionResponse(
                any(TransactionsModel.class), any(ProviderTransactionResponse.class),
                any(UUID.class), any(Instant.class))).thenReturn(response);
        when(transactionMapper.toEntity(response)).thenReturn(entity);
        when(transactionRepository.save(entity)).thenReturn(entity);
        when(transactionMapper.toTransactionResponse(entity)).thenReturn(response);

        TransactionService service = new TransactionService(providerClient, transactionMapper, transactionRepository);

        assertEquals("REJECTED", service.executeTransaction(request).status());
        verify(transactionRepository).save(entity);
    }

    @Test
    @SuppressWarnings("unchecked")
    void findTransactionsUsesFiltersAndPagination() {
        Transaction entity = Transaction.builder().id("id-1").build();
        when(transactionRepository.findAll(any(Specification.class), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(transactionMapper.toTransactionResponse(entity)).thenReturn(response("EXECUTED", "txn-789"));

        TransactionService service = new TransactionService(providerClient, transactionMapper, transactionRepository);

        assertEquals(1, service.findTransactions("acc-123456", "EXECUTED", "CREDIT", 10, 10).size());
        verify(transactionRepository).findAll(any(Specification.class), any(PageRequest.class));
    }

    private TransactionResponse response(String status, String providerTransactionId) {
        return new TransactionResponse(
                "id-1", "acc-123456", "CREDIT", new BigDecimal("1500.00"), "MXN",
                "Transferencia", status, providerTransactionId, new BigDecimal("5500.00"), Instant.now());
    }
}