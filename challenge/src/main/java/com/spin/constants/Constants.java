package com.spin.constants;

public final class Constants {
    public static final String API_BASE_PATH = "/api/v1";
    public static final int MAX_ACCOUNT_ID_LENGTH = 50;
    public static final int MAX_AMOUNT_INTEGER_DIGITS = 15;
    public static final int MAX_AMOUNT_FRACTION_DIGITS = 2;
    public static final int MAX_DESCRIPTION_LENGTH = 50;

    public static final String API_TAG = "Transactions";
    public static final String API_DESCRIPTION = "Operations related to transactions.";
    public static final String GET_TRANSACTIONS_SUMMARY = "Retrieve transactions";
    public static final String GET_TRANSACTIONS_DESCRIPTION = "Retrieves the available transactions for the current account context.";
    public static final String SUCCESS_RESPONSE = "Transactions retrieved successfully.";
    public static final String BAD_REQUEST_RESPONSE = "The request is invalid.";
    public static final String INTERNAL_SERVER_ERROR_RESPONSE = "An unexpected error occurred while retrieving transactions.";

    public static final String MODEL_DESCRIPTION = "Representa una transacción financiera asociada a una cuenta.";

    public static final String ACCOUNT_ID_DESCRIPTION = "Identificador de la cuenta.";
    public static final String ACCOUNT_ID_EXAMPLE = "acc-12345";
    public static final String ACCOUNT_ID_REQUIRED_MESSAGE = "El identificador de cuenta es obligatorio.";
    public static final String ACCOUNT_ID_SIZE_MESSAGE = "El identificador de cuenta no puede superar los 50 caracteres.";

    public static final String TYPE_DESCRIPTION = "Tipo de transaccion.";
    public static final String TYPE_EXAMPLE = "CREDIT o DEBIT";
    public static final String TYPE_REQUIRED_MESSAGE = "El tipo de transaccion es obligatorio.";
    public static final String TYPE_PATTERN = "CREDIT|DEBIT";
    public static final String TYPE_PATTERN_MESSAGE = "El tipo de transaccion debe ser CREDIT o DEBIT.";
    public static final String DEBIT_TYPE = "DEBIT";

    public static final String AMOUNT_DESCRIPTION = "Monto de la transaccion.";
    public static final String AMOUNT_EXAMPLE = "1500.00";
    public static final String TRANSACTION_REQUEST_EXAMPLE = "{\"accountId\":\"acc-12345\",\"type\":\"CREDIT\",\"amount\":\"1500.00\",\"currency\":\"MXN\",\"description\":\"Transferencia recibida.\"}";
    public static final String AMOUNT_REQUIRED_MESSAGE = "El monto es obligatorio.";
    public static final String AMOUNT_MINIMUM = "1.00";
    public static final String AMOUNT_MINIMUM_MESSAGE = "El monto debe ser mayor que uno.";
    public static final String AMOUNT_DIGITS_MESSAGE = "El monto debe tener hasta 15 enteros y 2 decimales.";
    public static final String MAX_DEBIT_AMOUNT = "10000.00";
    public static final String MAX_DEBIT_AMOUNT_MESSAGE = "El monto de una transaccion DEBIT no puede superar 10000.";

    public static final String CURRENCY_DESCRIPTION = "Tipo de moneda.";
    public static final String CURRENCY_EXAMPLE = "MXN";
    public static final String CURRENCY_REQUIRED_MESSAGE = "La moneda es obligatoria.";
    public static final String CURRENCY_PATTERN = "MXN";
    public static final String CURRENCY_PATTERN_MESSAGE = "La moneda debe ser MXN.";

    public static final String DESCRIPTION_DESCRIPTION = "Descripcion de la transaccion.";
    public static final String DESCRIPTION_EXAMPLE = "Transferencia recibida.";
    public static final String DESCRIPTION_SIZE_MESSAGE = "La descripcion no puede superar los 255 caracteres.";

    private Constants() {
    }
}