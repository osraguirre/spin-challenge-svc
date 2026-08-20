package com.spin.validation;

import com.spin.model.TransactionsModel;
import com.spin.constants.Constants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class DebitAmountLimitValidator implements ConstraintValidator<DebitAmountLimit, TransactionsModel> {
    private static final BigDecimal MAX_DEBIT_AMOUNT = new BigDecimal(Constants.MAX_DEBIT_AMOUNT);

    @Override
    public boolean isValid(TransactionsModel transaction, ConstraintValidatorContext context) {
        if (transaction == null || transaction.getType() == null || transaction.getAmount() == null) {
            return true;
        }

        if (!Constants.DEBIT_TYPE.equalsIgnoreCase(transaction.getType())) {
            return true;
        }

        return transaction.getAmount().compareTo(MAX_DEBIT_AMOUNT) <= 0;
    }
}
