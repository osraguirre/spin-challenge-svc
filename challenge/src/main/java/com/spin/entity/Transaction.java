package com.spin.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Builder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "transacciones", indexes = {
    @Index(name = "idx_transacciones_cuenta_fecha_creacion", columnList = "cuenta_id, fecha_creacion"),
    @Index(name = "idx_transacciones_transaccion_proveedor_id", columnList = "transaccion_proveedor_id")
})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Transaction {

    @Id
    @Column(name = "id", nullable = false, length = 36, updatable = false)
    private String id;

    @Column(name = "cuenta_id", nullable = false, length = 50)
    private String accountId;

    @Column(name = "tipo_transaccion", nullable = false, length = 10)
    private String type;

    @Column(name = "monto", nullable = false, precision = 17, scale = 2)
    private BigDecimal amount;

    @Column(name = "moneda", nullable = false, length = 3)
    private String currency;

    @Column(name = "descripcion", length = 50)
    private String description;

    @Column(name = "estado", nullable = false, length = 20)
    private String status;

    @Column(name = "transaccion_proveedor_id", length = 100)
    private String providerTransactionId;

    @Column(name = "saldo_posterior", precision = 17, scale = 2)
    private BigDecimal balanceAfter;

    @Column(name = "fecha_creacion", nullable = false)
    private Instant createdAt;

}