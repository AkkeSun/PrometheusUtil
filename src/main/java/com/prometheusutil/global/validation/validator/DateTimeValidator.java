package com.prometheusutil.global.validation.validator;

import com.prometheusutil.global.validation.DateTime;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeValidator implements ConstraintValidator<DateTime, String> {

    @Override
    public boolean isValid(String input, ConstraintValidatorContext context) {
        if (input == null) {
            return true;
        }
        try {
            LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyyMMdd"));
            return true;
        } catch (DateTimeParseException ignored) {
            return false;
        }
    }
}
