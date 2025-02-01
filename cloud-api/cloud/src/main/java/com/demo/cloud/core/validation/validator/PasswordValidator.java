package com.demo.cloud.core.validation.validator;

import com.demo.cloud.core.validation.annotation.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.PasswordData;
import org.passay.RuleResult;

import java.util.List;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    private String field;
    private final org.passay.PasswordValidator validator;

    public PasswordValidator(org.passay.PasswordValidator validator) {
        this.validator = validator;
    }

    @Override
    public void initialize(Password constraintAnnotation) {
        field = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            setUpContext(context, field + " must not be null.");
            return false;
        }

        PasswordData data = new PasswordData(password);
        RuleResult result = validator.validate(data);

        if (!result.isValid()) {
            List<String> messages = validator.getMessages(result);
            String fullMessage = String.join("|", messages);
            if (!field.equals("Password")) {
                fullMessage = fullMessage.replaceAll("Password", field);
            }

            setUpContext(context, fullMessage);
            return false;
        }

        return true;
    }

    private void setUpContext(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context
                .buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }
}
