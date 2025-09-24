package com.bookstore.shared;

import java.util.UUID;

public class BaseActions {
    private static String lastUsername;
    private static String lastPassword;
    private static String lastToken;
    private static String lastUserId;

    public static String generateUsername() {
        lastUsername = "user_" + UUID.randomUUID().toString().substring(0, 5);
        return lastUsername;
    }

    public static String generatePassword() {
        lastPassword = "P@ssw0rd_" + UUID.randomUUID().toString().substring(0, 5);
        return lastPassword;
    }

    public static String getLastUsername() {
        return lastUsername;
    }

    public static String getLastPassword() {
        return lastPassword;
    }

    public static void setLastToken(String token) {
        lastToken = token;
    }

    public static String getLastToken() {
        return lastToken;
    }

    public static void setLastUserId(String userId) {
        lastUserId = userId;
    }

    public static String getLastUserId() {
        return lastUserId;
    }
}
