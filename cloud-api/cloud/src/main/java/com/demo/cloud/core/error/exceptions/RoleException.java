package com.demo.cloud.core.error.exceptions;

import org.springframework.security.authorization.AuthorizationDeniedException;

public class RoleException extends AuthorizationDeniedException {
    public RoleException(String role) {
        super("User does not have required role: " + role);
    }
}
