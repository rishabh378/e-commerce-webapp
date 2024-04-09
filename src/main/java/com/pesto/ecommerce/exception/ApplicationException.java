package com.pesto.ecommerce.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException {

    private final String internalMessage;
    private final HttpStatus status;

    public ApplicationException(String message) {
        super(message);
        this.internalMessage = message;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ApplicationException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        internalMessage = message;
    }
}
