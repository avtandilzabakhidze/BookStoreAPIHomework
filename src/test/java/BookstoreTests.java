import com.bookstore.data.Constants;
import com.bookstore.models.BookRequest;
import com.bookstore.models.BookResponse;
import com.bookstore.models.ErrorResponse;
import com.bookstore.shared.BaseActions;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BookstoreTests extends BaseTest {
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
