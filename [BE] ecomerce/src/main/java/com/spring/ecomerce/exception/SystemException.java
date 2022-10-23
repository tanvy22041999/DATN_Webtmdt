package com.spring.ecomerce.exception;

import org.springframework.stereotype.Component;

@Component
public class SystemException extends Exception{
    public SystemException() {
        super();
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message, Throwable cause){
        super(message, cause);
    }
}
