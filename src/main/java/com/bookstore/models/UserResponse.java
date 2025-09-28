package com.bookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {
    private String userID;
    private String username;
    private List<Object> books;

    public String getUserID() { return userID; }
    public String getUsername() { return username; }
    public List<Object> getBooks() { return books; }
}
