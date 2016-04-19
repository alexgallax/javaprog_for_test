package bugify.tests;

import bugify.appmanager.ApplicationManager;
import bugify.model.Issue;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

public class TestBase {

    private static final String RESOLVED = "Resolved";
    private static final String CLOSED = "Closed";

    protected static final ApplicationManager app = new ApplicationManager();

    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite
    public void tearDown() {
        app.stop();
    }

    public void skipIfNotFixed(int issueId) throws IOException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

    private boolean isIssueOpen(int issueId) throws IOException {
        Issue issue = app.restAssured().getIssue(issueId);

        return !(issue.getStateName().equals(RESOLVED) || issue.getStateName().equals(CLOSED));
    }
}
