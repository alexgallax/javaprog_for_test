package bugify.tests;

import org.testng.annotations.Test;

import java.io.IOException;

public class RestTests extends TestBase {

    @Test
    public void getIssue() throws IOException {
        app.rest().getIssue(1);
    }
}
