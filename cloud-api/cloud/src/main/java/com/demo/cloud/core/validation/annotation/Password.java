package com.demo.cloud.core.validation.annotation;

import com.demo.cloud.core.validation.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
    String field() default "Password";

    String message() default "{default.invalid.password.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
