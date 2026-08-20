package com.spin.model;

import java.math.BigDecimal;

import com.spin.constants.TransactionConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import com.spin.validation.DebitAmountLimit;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@DebitAmountLimit
@Schema(description = TransactionConstants.MODEL_DESCRIPTION)
public class TransactionsModel {

    @Schema(description = TransactionConstants.ACCOUNT_ID_DESCRIPTION, requiredMode = Schema.RequiredMode.REQUIRED,
        example = TransactionConstants.ACCOUNT_ID_EXAMPLE)
    @NotBlank(message = TransactionConstants.ACCOUNT_ID_REQUIRED_MESSAGE)
    @Size(max = TransactionConstants.MAX_ACCOUNT_ID_LENGTH, message = TransactionConstants.ACCOUNT_ID_SIZE_MESSAGE)
    private String accountId;

    @Schema(description = TransactionConstants.TYPE_DESCRIPTION, example = TransactionConstants.TYPE_EXAMPLE)
    @NotBlank(message = TransactionConstants.TYPE_REQUIRED_MESSAGE)
    @Pattern(regexp = TransactionConstants.TYPE_PATTERN, message = TransactionConstants.TYPE_PATTERN_MESSAGE)
    private String type;

    @Schema(description = TransactionConstants.AMOUNT_DESCRIPTION,
        minimum = TransactionConstants.AMOUNT_MINIMUM,
        exclusiveMinimum = true,
        example = TransactionConstants.AMOUNT_EXAMPLE)
    @NotNull(message = TransactionConstants.AMOUNT_REQUIRED_MESSAGE)
    @DecimalMin(value = TransactionConstants.AMOUNT_MINIMUM, inclusive = false,
        message = TransactionConstants.AMOUNT_MINIMUM_MESSAGE)
    @Digits(integer = TransactionConstants.MAX_AMOUNT_INTEGER_DIGITS,
        fraction = TransactionConstants.MAX_AMOUNT_FRACTION_DIGITS,
        message = TransactionConstants.AMOUNT_DIGITS_MESSAGE)
    private BigDecimal amount;

    @Schema(description = TransactionConstants.CURRENCY_DESCRIPTION, example = TransactionConstants.CURRENCY_EXAMPLE)
    @NotBlank(message = TransactionConstants.CURRENCY_REQUIRED_MESSAGE)
    @Pattern(regexp = TransactionConstants.CURRENCY_PATTERN, message = TransactionConstants.CURRENCY_PATTERN_MESSAGE)
    private String currency;

    @Schema(description = TransactionConstants.DESCRIPTION_DESCRIPTION, example = TransactionConstants.DESCRIPTION_EXAMPLE)
    @Size(max = TransactionConstants.MAX_DESCRIPTION_LENGTH, message = TransactionConstants.DESCRIPTION_SIZE_MESSAGE)
    private String description;

}
