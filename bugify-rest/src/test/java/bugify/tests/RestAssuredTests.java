package bugify.tests;

import bugify.model.Issue;
import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static bugify.appmanager.consts.AppConsts.REST_KEY_PROP;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RestAssuredTests extends TestBase {

    private static final int ISSUE_ID = 1;

    @BeforeClass
    public void init() {
        RestAssured.authentication = RestAssured.basic(app.getProperty(REST_KEY_PROP), "");
    }

    @Test
    public void testGetIssue() throws IOException {
        app.restAssured().getIssue(ISSUE_ID);
    }

    @Test
    public void testCreateIssue() throws IOException {
        Set<Issue> beforeIssues = app.restAssured().getIssues();
        Issue newIssue = new Issue()
                .withSubject("Test subject")
                .withDescription("Test description");

        int issueId = app.restAssured().createIssue(newIssue);

        Set<Issue> afterIssues = app.restAssured().getIssues();
        beforeIssues.add(newIssue.withId(issueId));

        assertThat(afterIssues, equalTo(beforeIssues));
    }

    @Test
    public void skipTestIfOpenIssue() throws IOException {
        skipIfNotFixed(ISSUE_ID);

        testCreateIssue();
    }
}
