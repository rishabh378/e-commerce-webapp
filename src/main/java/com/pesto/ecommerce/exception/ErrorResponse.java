package com.pesto.ecommerce.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorResponse {
    private int code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> errors;

}
