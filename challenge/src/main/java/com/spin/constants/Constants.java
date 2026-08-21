package com.spin.constants;

import java.math.BigDecimal;

public final class Constants {
    public static final int MAX_ACCOUNT_ID_LENGTH = 50;
    public static final int MAX_AMOUNT_INTEGER_DIGITS = 15;
    public static final int MAX_AMOUNT_FRACTION_DIGITS = 2;
    public static final int MAX_DESCRIPTION_LENGTH = 50;

    public static final String API_TAG = "Transacciones";
    public static final String API_DESCRIPTION = "Operaciones relacionadas con transacciones.";
    public static final String EXECUTE_TRANSACTION_SUMMARY = "Ejecutar transaccion";
    public static final String EXECUTE_TRANSACTION_DESCRIPTION = "Ejecuta una transaccion de credito o debito contra el proveedor y persiste el resultado.";
    public static final String GET_TRANSACTIONS_SUMMARY = "Consultar transacciones";
    public static final String GET_TRANSACTIONS_DESCRIPTION = "Recupera las transacciones disponibles para el contexto de cuenta actual.";
    public static final String ACCOUNT_ID_FILTER_DESCRIPTION = "Filtra por identificador de cuenta.";
    public static final String STATUS_FILTER_DESCRIPTION = "Filtra por estado de la transaccion.";
    public static final String TYPE_FILTER_DESCRIPTION = "Filtra por tipo de transaccion.";
    public static final String PAGE_DESCRIPTION = "Numero de pagina, inicia en 0.";
    public static final String LIMIT_DESCRIPTION = "Cantidad maxima de resultados por pagina, entre 1 y 100.";
    public static final String SUCCESS_RESPONSE = "Transaccion procesada exitosamente.";
    public static final String GET_TRANSACTIONS_SUCCESS_RESPONSE = "Transacciones recuperadas exitosamente.";
    public static final String BAD_REQUEST_RESPONSE = "La solicitud es invalida.";
    public static final String INTERNAL_SERVER_ERROR_RESPONSE = "Ocurrio un error inesperado al procesar la transaccion.";

    public static final String MODEL_DESCRIPTION = "Representa una transacción financiera asociada a una cuenta.";

    public static final String ACCOUNT_ID_DESCRIPTION = "Identificador de la cuenta.";
    public static final String ACCOUNT_ID_EXAMPLE = "acc-12345";
    public static final String ACCOUNT_ID_REQUIRED_MESSAGE = "El identificador de cuenta es obligatorio.";
    public static final String ACCOUNT_ID_SIZE_MESSAGE = "El identificador de cuenta no puede superar los 50 caracteres.";

    public static final String TYPE_DESCRIPTION = "Tipo de transaccion.";
    public static final String TYPE_EXAMPLE = "CREDIT o DEBIT";
    public static final String TYPE_REQUIRED_MESSAGE = "El tipo de transaccion es obligatorio.";
    public static final String TYPE_PATTERN = "CREDIT|DEBIT";
    public static final String TYPE_EXAMPLE_CREDIT = "CREDIT";
    public static final String TYPE_PATTERN_MESSAGE = "El tipo de transaccion debe ser CREDIT o DEBIT.";
    public static final String DEBIT_TYPE = "DEBIT";

    public static final String AMOUNT_DESCRIPTION = "Monto de la transaccion.";
    public static final String AMOUNT_EXAMPLE = "1500.00";
    public static final String TRANSACTION_REQUEST_EXAMPLE = "{\"accountId\":\"acc-12345\",\"type\":\"CREDIT\",\"amount\":\"1500.00\",\"currency\":\"MXN\",\"description\":\"Transferencia recibida.\"}";
    public static final String AMOUNT_REQUIRED_MESSAGE = "El monto es obligatorio.";
    public static final String AMOUNT_MINIMUM = "1.00";
    public static final String AMOUNT_MINIMUM_MESSAGE = "El monto debe ser mayor que uno.";
    public static final String AMOUNT_DIGITS_MESSAGE = "El monto debe tener hasta 15 enteros y 2 decimales.";
    public static final BigDecimal MAX_DEBIT_AMOUNT = new BigDecimal("10000.00");
    public static final String MAX_DEBIT_AMOUNT_MESSAGE = "El monto de una transaccion DEBIT no puede superar 10000.";

    public static final String CURRENCY_DESCRIPTION = "Tipo de moneda.";
    public static final String CURRENCY_EXAMPLE = "MXN";
    public static final String CURRENCY_REQUIRED_MESSAGE = "La moneda es obligatoria.";
    public static final String CURRENCY_PATTERN = "MXN";
    public static final String CURRENCY_PATTERN_MESSAGE = "La moneda debe ser MXN.";

    public static final String DESCRIPTION_DESCRIPTION = "Descripcion de la transaccion.";
    public static final String DESCRIPTION_EXAMPLE = "Transferencia recibida.";
    public static final String DESCRIPTION_SIZE_MESSAGE = "La descripcion no puede superar los 50 caracteres.";

    public static final String RESPONSE_MODEL_DESCRIPTION = "Representa el resultado de una transaccion ejecutada.";
    public static final String ID_DESCRIPTION = "Identificador interno de la transaccion.";
    public static final String ID_EXAMPLE = "3fa85f64-5717-4562-b3fc-2c963f66afa6";
    public static final String STATUS_DESCRIPTION = "Estado de la transaccion.";
    public static final String STATUS_EXAMPLE = "EXECUTED";
    public static final String PROVIDER_TRANSACTION_ID_DESCRIPTION = "Identificador de la transaccion generado por el proveedor.";
    public static final String PROVIDER_TRANSACTION_ID_EXAMPLE = "txn-789";
    public static final String BALANCE_AFTER_DESCRIPTION = "Saldo de la cuenta despues de ejecutar la transaccion.";
    public static final String BALANCE_AFTER_EXAMPLE = "5500.00";
    public static final String CREATED_AT_DESCRIPTION = "Fecha y hora en que se registro la transaccion.";
    public static final String CREATED_AT_EXAMPLE = "2025-03-15T10:30:00Z";

    private Constants() {
    }
}