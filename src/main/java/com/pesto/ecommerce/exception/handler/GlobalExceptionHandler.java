package com.pesto.ecommerce.exception.handler;

import com.pesto.ecommerce.exception.ApplicationException;
import com.pesto.ecommerce.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleAdminException(ApplicationException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(exception.getStatus().value())
                .message(exception.getInternalMessage())
                .build();
        return ResponseEntity.status(exception.getStatus()).body(errorResponse);
    }

    /*@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = exception.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(status.value())
                .message(errorMessage)
                .build();
        return ResponseEntity.status(status).body(errorResponse);
    }*/
}
