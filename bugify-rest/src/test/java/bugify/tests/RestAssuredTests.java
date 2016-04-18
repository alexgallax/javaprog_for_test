package bugify.tests;

import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static bugify.appmanager.consts.AppConsts.REST_KEY_PROP;

public class RestAssuredTests extends TestBase {

    @BeforeClass
    public void init() {
        RestAssured.authentication = RestAssured.basic(app.getProperty(REST_KEY_PROP), "");
    }

    @Test
    public void getIssue() throws IOException {
        app.restAssured().getIssue(1);
    }
}
