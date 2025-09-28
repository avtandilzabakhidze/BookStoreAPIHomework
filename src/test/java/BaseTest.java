import com.bookstore.data.Constants;
import com.bookstore.shared.BaseActions;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import static io.restassured.http.ContentType.JSON;

public class BaseTest {
    @BeforeSuite
    public void setup() {
        BaseActions.registerUser();
        BaseActions.generateToken();
    }

    @BeforeMethod
    public void beforeMethod() {
        RequestSpecification spec = new RequestSpecBuilder()
                .setBaseUri(Constants.BASE_URI)
                .setContentType(JSON)
                .setAuth(RestAssured.oauth2(BaseActions.token))
                .build();

        RestAssured.requestSpecification = spec;
    }
}
