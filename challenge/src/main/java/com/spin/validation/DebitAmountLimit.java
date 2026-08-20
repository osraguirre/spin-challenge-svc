package com.spin.validation;

import com.spin.constants.Constants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = DebitAmountLimitValidator.class)
@Target(TYPE)
@Retention(RUNTIME)
public @interface DebitAmountLimit {
    String message() default Constants.MAX_DEBIT_AMOUNT_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
