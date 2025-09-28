import com.bookstore.data.Constants;
import com.bookstore.models.BookRequest;
import com.bookstore.models.BookResponse;
import com.bookstore.models.ErrorResponse;
import com.bookstore.models.Isbns;
import com.bookstore.shared.BaseActions;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.bookstore.data.TestData.*;

public class BookstoreTests extends BaseTest {
    @Test(priority = 1, description = "Verify that a single book can be added successfully with a valid ISBN")
    public void testAddBookWithValidIsbn() {
        BookRequest request = new BookRequest(BaseActions.userId, VALID_ISBN_1);

        BookResponse response = RestAssured.given()
                .body(request)
                .when()
                .post(Constants.BOOKS_ENDPOINT)
                .then()
                .statusCode(201)
                .extract().as(BookResponse.class);

        Assert.assertEquals(response.getBooks().getFirst().getIsbn(), VALID_ISBN_1, "First book ISBN should match the added ISBN");
    }

    @Test(priority = 2, description = "Verify that multiple books can be added successfully in a single request")
    public void testAddMultipleBooks() {
        List<Isbns> isbns = Arrays.asList(
                new Isbns(VALID_ISBN_1),
                new Isbns(VALID_ISBN_2)
        );

        BookRequest request = new BookRequest(BaseActions.userId, isbns);

        BookResponse response = RestAssured.given()
                .body(request)
                .when()
                .post(Constants.BOOKS_ENDPOINT)
                .then()
                .statusCode(201)
                .extract().as(BookResponse.class);

        Assert.assertEquals(response.getBooks().size(), isbns.size(), "Number of books should match the request");
    }

    //negative tests
    @Test(priority = 3, description = "Verify that adding a book with an invalid ISBN proper error message And Error code should be 1205")
    public void testAddBookWithInvalidIsbn() {
        BookRequest request = new BookRequest(BaseActions.userId, "-1");

        ErrorResponse errorResponse = RestAssured.given()
                .body(request)
                .when()
                .post(Constants.BOOKS_ENDPOINT)
                .then()
                .statusCode(400)
                .extract().as(ErrorResponse.class);

        Assert.assertEquals(errorResponse.getCode(), "1205", "Error code should be 1205");
        Assert.assertTrue(errorResponse.getMessage().contains("ISBN"), "Error message should mention ISBN");
    }

    @Test(priority = 4, description = "Verify that adding a book with an empty ISBN proper error message And Error code should be 1205")
    public void testAddBookWithEmptyIsbn() {
        BookRequest request = new BookRequest(BaseActions.userId, "");

        ErrorResponse errorResponse = RestAssured.given()
                .body(request)
                .when()
                .post(Constants.BOOKS_ENDPOINT)
                .then()
                .statusCode(400)
                .extract().as(ErrorResponse.class);

        Assert.assertEquals(errorResponse.getCode(), "1205", "Error code should be 1205");
        Assert.assertEquals(errorResponse.getMessage(),"ISBN supplied is not available in Books Collection!","Error message should exactly match for empty ISBN"
        );

    }

    @Test(priority = 5, description = "Verify that adding a book with a empty UserId returns proper error message And Error code should be 1207")
    public void testAddBookWithEmptyUserId() {
        BookRequest request = new BookRequest(null, VALID_ISBN_1);

        ErrorResponse errorResponse = RestAssured.given()
                .body(request)
                .when()
                .post(Constants.BOOKS_ENDPOINT)
                .then()
                .statusCode(401)
                .extract().as(ErrorResponse.class);

        Assert.assertEquals(errorResponse.getCode(), "1207", "Error code should be 1207");
        Assert.assertEquals(errorResponse.getMessage(), "User Id not correct!", "Error message should indicate Correct User ID access");
    }

    @Test(priority = 6, description = "Verify that adding a book with an invalid UserId returns proper error message And Error code should be 1207")
    public void testAddBookWithInvalidUserId() {
        BookRequest request = new BookRequest("invalid-user", VALID_ISBN_1);

        ErrorResponse errorResponse = RestAssured.given()
                .body(request)
                .when()
                .post(Constants.BOOKS_ENDPOINT)
                .then()
                .statusCode(401)
                .extract().as(ErrorResponse.class);

        Assert.assertEquals(errorResponse.getCode(), "1207", "Error code should be 1207");
        Assert.assertEquals(errorResponse.getMessage(), "User Id not correct!", "Error message should indicate Correct User ID access");
    }

    @Test(priority = 7, description = "Verify that adding a book without authentication returns proper error message And Error code should be 1200")
    public void testAddBookWithoutAuth() {
        BookRequest request = new BookRequest(BaseActions.userId, VALID_ISBN_1);

        ErrorResponse errorResponse = RestAssured.given()
                .auth().none()
                .body(request)
                .when()
                .post(Constants.BOOKS_ENDPOINT)
                .then()
                .statusCode(401)
                .extract()
                .as(ErrorResponse.class);

        Assert.assertEquals(errorResponse.getCode(), "1200", "Error code should be 1200");
        Assert.assertEquals(errorResponse.getMessage(), "User not authorized!", "Error message should indicate unauthorized access");
    }
}
