package bugify.tests;

import bugify.model.Issue;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RestTests extends TestBase {

    @Test
    public void getIssue() throws IOException {
        app.rest().getIssue(1);
    }

    @Test
    public void createIssue() throws IOException {
        Set<Issue> beforeIssues = app.rest().getIssues();
        Issue newIssue = new Issue()
                .withSubject("Test subject")
                .withDescription("Test description");

        int issueId = app.rest().createIssue(newIssue);

        Set<Issue> afterIssues = app.rest().getIssues();
        beforeIssues.add(newIssue.withId(issueId));

        assertThat(afterIssues, equalTo(beforeIssues));
    }
}
