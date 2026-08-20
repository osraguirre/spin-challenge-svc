package com.spin.repository;

import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepository {
    public String getTransactionsMessage() {
        return "Transactions retrieved successfully";
    }
}
