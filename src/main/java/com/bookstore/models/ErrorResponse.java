package com.bookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
    private String code;
    private String message;

    public String getCode() { return code; }
    public String getMessage() { return message; }
}
