package com.bookstore.shared;

import com.bookstore.data.Constants;
import com.bookstore.models.*;
import io.restassured.RestAssured;

import java.util.UUID;

import static io.restassured.http.ContentType.JSON;

public class BaseActions {
    public static String token;
    public static String userId;
    public static String username;
    public static String password;

    public static void registerUser() {
        RestAssured.baseURI = Constants.BASE_URI;
        generateCredentials();

        UserRequest userRequest = new UserRequest(username, password);
        UserResponse userResponse = RestAssured.given()
                .contentType(JSON)
                .body(userRequest)
                .when()
                .post(Constants.USER_ENDPOINT)
                .then()
                .statusCode(201)
                .extract()
                .as(UserResponse.class);

        userId = userResponse.getUserID();
    }

    public static void generateToken() {
        TokenRequest tokenRequest = new TokenRequest(username, password);
        TokenResponse tokenResponse = RestAssured.given()
                .contentType(JSON)
                .body(tokenRequest)
                .when()
                .post(Constants.TOKEN_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .as(TokenResponse.class);

        token = tokenResponse.getToken();
    }

    private static void generateCredentials() {
        String suffix = UUID.randomUUID().toString().substring(0, 5);
        username = "user_" + suffix;
        password = "Passw0rd@" + suffix;
    }
}
