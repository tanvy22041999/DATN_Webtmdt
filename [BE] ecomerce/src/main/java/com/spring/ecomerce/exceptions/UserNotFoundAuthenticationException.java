package com.spring.ecomerce.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserNotFoundAuthenticationException extends AuthenticationException {
    public UserNotFoundAuthenticationException(String msg) {
        super(msg);
    }
}
