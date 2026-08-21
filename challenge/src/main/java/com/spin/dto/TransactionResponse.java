package com.spin.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.spin.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = Constants.RESPONSE_MODEL_DESCRIPTION)
public record TransactionResponse(
        @Schema(description = Constants.ID_DESCRIPTION, example = Constants.ID_EXAMPLE)
        String id,

        @Schema(description = Constants.ACCOUNT_ID_DESCRIPTION, example = Constants.ACCOUNT_ID_EXAMPLE)
        String accountId,

        @Schema(description = Constants.TYPE_DESCRIPTION, example = Constants.TYPE_EXAMPLE)
        String type,

        @Schema(description = Constants.AMOUNT_DESCRIPTION, example = Constants.AMOUNT_EXAMPLE)
        BigDecimal amount,

        @Schema(description = Constants.CURRENCY_DESCRIPTION, example = Constants.CURRENCY_EXAMPLE)
        String currency,

        @Schema(description = Constants.DESCRIPTION_DESCRIPTION, example = Constants.DESCRIPTION_EXAMPLE)
        String description,

        @Schema(description = Constants.STATUS_DESCRIPTION, example = Constants.STATUS_EXAMPLE)
        String status,

        @Schema(description = Constants.PROVIDER_TRANSACTION_ID_DESCRIPTION, example = Constants.PROVIDER_TRANSACTION_ID_EXAMPLE)
        String providerTransactionId,

        @Schema(description = Constants.BALANCE_AFTER_DESCRIPTION, example = Constants.BALANCE_AFTER_EXAMPLE)
        BigDecimal balanceAfter,

        @Schema(description = Constants.CREATED_AT_DESCRIPTION, example = Constants.CREATED_AT_EXAMPLE)
        Instant createdAt) {
}