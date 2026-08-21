package com.spin.repository;

import com.spin.entity.Transaction;

import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;

public final class TransactionSpecifications {

    private TransactionSpecifications() {
    }

    public static Specification<Transaction> withFilters(String accountId, String status, String type) {
        return accountIdEquals(accountId)
                .and(statusEquals(status))
                .and(typeEquals(type));
    }

    private static Specification<Transaction> accountIdEquals(String accountId) {
        return Objects.isNull(accountId) || accountId.isBlank()
                ? Specification.unrestricted()
                : (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("accountId"), accountId);
    }

    private static Specification<Transaction> statusEquals(String status) {
        return Objects.isNull(status) || status.isBlank()
                ? Specification.unrestricted()
                : (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    private static Specification<Transaction> typeEquals(String type) {
        return Objects.isNull(type) || type.isBlank()
                ? Specification.unrestricted()
                : (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("type"), type);
    }
}