package com.spin.service;

import com.spin.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public String getTransactions() {
        return transactionRepository.getTransactionsMessage();
    }
}
