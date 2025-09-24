import com.bookstore.shared.BaseActions;
import com.bookstore.shared.BookstoreSteps;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static com.bookstore.data.Constants.BOOKSTORE_BASE_URI;
import static com.bookstore.shared.BookstoreSteps.getFirstBookISBN;
import static io.restassured.http.ContentType.JSON;

public class BookstoreTest {
    @Test(priority = 1)
    public void userRegistrationWithValidCredentials() {
        String username = BaseActions.generateUsername();
        String password = BaseActions.generatePassword();
        String requestBody = BookstoreSteps.createUserPayload(username, password);

        String userId = RestAssured.given()
                .baseUri(BOOKSTORE_BASE_URI)
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("/Account/v1/User")
                .then()
                .statusCode(201)
                .extract()
                .path("userID");

        Assert.assertNotNull(userId, "userId should not be null");
        BaseActions.setLastUserId(userId);
    }

    @Test(priority = 2)
    public void userAuthorizationWithValidCredentials() {
        String username = BaseActions.getLastUsername();
        String password = BaseActions.getLastPassword();
        String requestBody = BookstoreSteps.createUserPayload(username, password);

        RestAssured.given()
                .baseUri(BOOKSTORE_BASE_URI)
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("/Account/v1/Authorized")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    @Test(priority = 3)
    public void generateTokenWithValidCredentials() {
        String username = BaseActions.getLastUsername();
        String password = BaseActions.getLastPassword();
        String requestBody = BookstoreSteps.createUserPayload(username, password);

        String token = RestAssured.given()
                .baseUri(BOOKSTORE_BASE_URI)
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        Assert.assertNotNull(token, "token should not be null");
        BaseActions.setLastToken(token);
    }

    @Test(priority = 4)
    public void getAllBooks() {
        List<String> isbns = RestAssured.given()
                .baseUri(BOOKSTORE_BASE_URI)
                .when()
                .get("/BookStore/v1/Books")
                .then()
                .statusCode(200)
                .extract()
                .path("books.isbn");

        String firstIsbn = isbns.getFirst();
        Assert.assertTrue(Long.parseLong(firstIsbn) > 0, "firstIsbn should be greater than 0");
        BookstoreSteps.setFirstBookISBN(firstIsbn);
    }

    @Test(priority = 5)
    public void addBookWithValidISBN() {
        String token = BaseActions.getLastToken();
        String userId = BaseActions.getLastUserId();

        String requestBody = BookstoreSteps.addBook(userId, getFirstBookISBN());

        RestAssured.given()
                .baseUri(BOOKSTORE_BASE_URI)
                .header("Authorization", "Bearer " + token)
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .statusCode(201);
    }

    @Test(priority = 6)
    public void addBookWithInvalidISBN() {
        String token = BaseActions.getLastToken();
        String userId = BaseActions.getLastUserId();

        String requestBody = BookstoreSteps.addBook(userId, "-1");

        RestAssured.given()
                .baseUri(BOOKSTORE_BASE_URI)
                .header("Authorization", "Bearer " + token)
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .statusCode(400);
    }
}
