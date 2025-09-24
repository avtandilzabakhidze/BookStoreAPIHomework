package com.bookstore.shared;

public class BookstoreSteps {
    public static String firstBookISBN;

    public static String createUserPayload(String username, String password) {
        return String.format("{\"userName\":\"%s\",\"password\":\"%s\"}", username, password);
    }

    public static String addBook(String userId, String isbn) {
        return String.format(
                "{\n" +
                        "  \"userId\": \"%s\",\n" +
                        "  \"collectionOfIsbns\": [\n" +
                        "    { \"isbn\": \"%s\" }\n" +
                        "  ]\n" +
                        "}", userId, isbn
        );
    }

    public static void setFirstBookISBN(String firstBookISBN) {
        BookstoreSteps.firstBookISBN = firstBookISBN;
    }

    public static String getFirstBookISBN() {
        return firstBookISBN;
    }
}
