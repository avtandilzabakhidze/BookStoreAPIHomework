package com.bookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {
    private String token;
    private String status;
    private String result;

    public String getToken() { return token; }
    public String getStatus() { return status; }
    public String getResult() { return result; }
}
