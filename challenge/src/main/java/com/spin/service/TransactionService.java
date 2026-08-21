package com.spin.service;

import com.spin.client.ProviderClient;
import com.spin.constants.Constants;
import com.spin.dto.ProviderTransactionRequest;
import com.spin.dto.ProviderTransactionResponse;
import com.spin.dto.TransactionResponse;
import com.spin.entity.Transaction;
import com.spin.exception.TransactionValidationException;
import com.spin.mapper.ProviderTransactionMapper;
import com.spin.model.TransactionsModel;
import com.spin.repository.TransactionRepository;
import com.spin.repository.TransactionSpecifications;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Coordina la ejecucion y consulta de transacciones financieras.
 * <p>
 * Aplica las reglas de negocio, delega la ejecucion al proveedor externo
 * y persiste la transaccion resultante.
 */
@Slf4j
@Service
public class TransactionService {
    private final ProviderClient providerClient;
    private final ProviderTransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;

    public TransactionService(
            ProviderClient providerClient,
            ProviderTransactionMapper transactionMapper,
            TransactionRepository transactionRepository) {
        this.providerClient = providerClient;
        this.transactionMapper = transactionMapper;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Valida las reglas de negocio, ejecuta la transaccion contra el proveedor
     * y persiste el resultado.
     *
     * @param transaction la transaccion solicitada
     * @return la transaccion persistida, incluyendo el resultado del proveedor
     * @throws TransactionValidationException si se viola una regla de negocio
     */
    public TransactionResponse executeTransaction(TransactionsModel transaction) {
        validateDebitLimit(transaction);

        UUID id = UUID.randomUUID();
        log.info("Transaction flow started, id: {}, request: {}", id, transaction);

        ProviderTransactionRequest providerRequest = transactionMapper.toProviderRequest(transaction);
        log.info("Calling provider, id: {}, request: {}", id, providerRequest);
        ProviderTransactionResponse providerResponse = providerClient.execute(providerRequest);
        log.info("Provider response received, id: {}, providerTransactionId: {}, status: {}",
            id, providerResponse.transactionId(), providerResponse.status());

        TransactionResponse response = transactionMapper.toTransactionResponse(
            transaction, providerResponse, id, Instant.now());
        Transaction savedTransaction = transactionRepository.save(transactionMapper.toEntity(response));
        TransactionResponse savedResponse = transactionMapper.toTransactionResponse(savedTransaction);
        log.info("Transaction persisted, id: {}, status: {}, providerTransactionId: {}",
            savedResponse.id(), savedResponse.status(), savedResponse.providerTransactionId());
        return savedResponse;
    }

    /**
     * Consulta las transacciones persistidas usando filtros opcionales y paginacion.
     *
     * @param accountId filtra por identificador de cuenta, o {@code null} para omitir
     * @param status filtra por estado de la transaccion, o {@code null} para omitir
     * @param type filtra por tipo de transaccion, o {@code null} para omitir
     * @param limit cantidad maxima de resultados por pagina
     * @param offset desplazamiento expresado como {@code page * limit} (debe ser múltiplo de {@code limit})
     * @return las transacciones que coinciden con los filtros
     */
    public List<TransactionResponse> findTransactions(
            String accountId, String status, String type, int limit, int offset) {
        int page = offset / limit;
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt").and(Sort.by(Sort.Direction.DESC, "id"));
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<Transaction> transactions = transactionRepository.findAll(
            TransactionSpecifications.withFilters(accountId, status, type), pageable);
        log.info("Transactions queried, accountId: {}, status: {}, type: {}, page: {}, limit: {}, results: {}",
            accountId, status, type, page, limit, transactions.getNumberOfElements());
        return transactions.getContent().stream()
            .map(transactionMapper::toTransactionResponse)
            .toList();
    }

    /**
     * Rechaza transacciones DEBIT que superen el monto maximo permitido; CREDIT no tiene limite.
     */
    private void validateDebitLimit(TransactionsModel transaction) {
        if (Constants.DEBIT_TYPE.equalsIgnoreCase(transaction.getType())
                && transaction.getAmount() != null
                && transaction.getAmount().compareTo(Constants.MAX_DEBIT_AMOUNT) > 0) {
            throw new TransactionValidationException(Constants.MAX_DEBIT_AMOUNT_MESSAGE);
        }
    }
}
