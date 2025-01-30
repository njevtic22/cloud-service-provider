package com.demo.cloud.core.error;

import java.util.List;

public record FieldErrorMessage(
        String field,
        Object rejectedValue,
        List<String> messages
) {
}
