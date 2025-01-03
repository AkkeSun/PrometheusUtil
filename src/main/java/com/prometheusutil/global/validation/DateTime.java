package com.prometheusutil.global.validation;

import com.prometheusutil.global.validation.validator.DateTimeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateTimeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateTime {

    String message() default "yyyyMMdd 형태만 입력 가능 합니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

