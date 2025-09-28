import com.bookstore.data.Constants;
import com.bookstore.models.BookRequest;
import com.bookstore.models.BookResponse;
import com.bookstore.models.ErrorResponse;
import com.bookstore.shared.BaseActions;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.http.ContentType.JSON;

public class BookstoreTests extends BaseTest {
    @BeforeMethod
    public void beforeMethod() {
        RequestSpecification spec = new RequestSpecBuilder()
                .setBaseUri(Constants.BASE_URI)
                .setContentType(JSON)
                .setAuth(RestAssured.oauth2(BaseActions.token))
                .build();

        RestAssured.requestSpecification = spec;
    }

    @Test(priority = 1)
    public void addBookPositive() {
        BookRequest request = new BookRequest(BaseActions.userId, "9781449325862");

        BookResponse response = RestAssured.given()
                .body(request)
                .when()
                .post(Constants.BOOKS_ENDPOINT)
                .then()
                .statusCode(201)
                .extract()
                .as(BookResponse.class);

        Assert.assertEquals(response.getBooks().getFirst().getIsbn(), "9781449325862", "First book ISBN should match the added ISBN");
    }

    @Test(priority = 2)
    public void addBookInvalidISBN() {
        BookRequest request = new BookRequest(BaseActions.userId, "-1");

        ErrorResponse errorResponse = RestAssured.given()
                .body(request)
                .when()
                .post(Constants.BOOKS_ENDPOINT)
                .then()
                .statusCode(400)
                .extract().as(ErrorResponse.class);

        Assert.assertEquals(errorResponse.getCode(), "1205");
        Assert.assertTrue(errorResponse.getMessage().contains("ISBN"));
    }
}
