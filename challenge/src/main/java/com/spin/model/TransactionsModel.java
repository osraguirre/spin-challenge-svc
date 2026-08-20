package com.spin.model;

import java.math.BigDecimal;

import com.spin.constants.Constants;
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
@Schema(description = Constants.MODEL_DESCRIPTION)
public class TransactionsModel {

    @Schema(description = Constants.ACCOUNT_ID_DESCRIPTION, requiredMode = Schema.RequiredMode.REQUIRED,
        example = Constants.ACCOUNT_ID_EXAMPLE)
    @NotBlank(message = Constants.ACCOUNT_ID_REQUIRED_MESSAGE)
    @Size(max = Constants.MAX_ACCOUNT_ID_LENGTH, message = Constants.ACCOUNT_ID_SIZE_MESSAGE)
    private String accountId;

    @Schema(description = Constants.TYPE_DESCRIPTION, example = Constants.TYPE_EXAMPLE)
    @NotBlank(message = Constants.TYPE_REQUIRED_MESSAGE)
    @Pattern(regexp = Constants.TYPE_PATTERN, message = Constants.TYPE_PATTERN_MESSAGE)
    private String type;

    @Schema(description = Constants.AMOUNT_DESCRIPTION,
        minimum = Constants.AMOUNT_MINIMUM,
        exclusiveMinimum = true,
        example = Constants.AMOUNT_EXAMPLE)
    @NotNull(message = Constants.AMOUNT_REQUIRED_MESSAGE)
    @DecimalMin(value = Constants.AMOUNT_MINIMUM, inclusive = false,
        message = Constants.AMOUNT_MINIMUM_MESSAGE)
    @Digits(integer = Constants.MAX_AMOUNT_INTEGER_DIGITS,
        fraction = Constants.MAX_AMOUNT_FRACTION_DIGITS,
        message = Constants.AMOUNT_DIGITS_MESSAGE)
    private BigDecimal amount;

    @Schema(description = Constants.CURRENCY_DESCRIPTION, example = Constants.CURRENCY_EXAMPLE)
    @NotBlank(message = Constants.CURRENCY_REQUIRED_MESSAGE)
    @Pattern(regexp = Constants.CURRENCY_PATTERN, message = Constants.CURRENCY_PATTERN_MESSAGE)
    private String currency;

    @Schema(description = Constants.DESCRIPTION_DESCRIPTION, example = Constants.DESCRIPTION_EXAMPLE)
    @Size(max = Constants.MAX_DESCRIPTION_LENGTH, message = Constants.DESCRIPTION_SIZE_MESSAGE)
    private String description;

}
