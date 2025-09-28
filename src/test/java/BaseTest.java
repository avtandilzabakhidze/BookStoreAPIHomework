import com.bookstore.shared.BaseActions;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    @BeforeSuite
    public void setup() {
        BaseActions.registerUser();
        BaseActions.generateToken();
    }
}
