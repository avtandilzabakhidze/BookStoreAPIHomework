package com.bookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookRequest {
    private String userId;
    private List<Isbns> collectionOfIsbns;

    public BookRequest(String userId, String isbn) {
        this.userId = userId;
        this.collectionOfIsbns = Collections.singletonList(new Isbns(isbn));
    }

    public BookRequest(String userId, List<String> isbns) {
        this.userId = userId;
        this.collectionOfIsbns = isbns.stream()
                .map(Isbns::new)
                .toList();
    }

    public String getUserId() {
        return userId;
    }

    public List<Isbns> getCollectionOfIsbns() {
        return collectionOfIsbns;
    }
}
