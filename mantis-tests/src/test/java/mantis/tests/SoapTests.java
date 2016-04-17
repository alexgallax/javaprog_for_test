package mantis.tests;

import mantis.model.Issue;
import mantis.model.Project;
import org.testng.annotations.Test;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class SoapTests extends TestBase {

    private static final String SUMMARY_TEXT = "Test summary";
    private static final String DESCRIPTION_TEXT = "Test description";

    private static final int ISSUE_ID = 000001;

    @Test
    public void testGetProjects() throws RemoteException, ServiceException, MalformedURLException {
        Set<Project> projects = app.soap().getProjects();

        System.out.println("Number of Projects: " + projects.size());
        System.out.println("Projects:\n" + projects);
    }

    @Test
    public void testGetIssue() throws RemoteException, ServiceException, MalformedURLException {
        Issue issue = app.soap().getIssue(ISSUE_ID);

        System.out.println(issue);
    }

    @Test
    public void testCreateIssue() throws RemoteException, ServiceException, MalformedURLException {
        Set<Project> projects = app.soap().getProjects();

        Issue newIssue = new Issue()
                .withSummary(SUMMARY_TEXT)
                .withDescription(DESCRIPTION_TEXT)
                .withProject(projects.iterator().next());

        Issue createdIssue = app.soap().createIssue(newIssue);

        assertThat(newIssue.getSummary(), equalTo(createdIssue.getSummary()));
        assertThat(newIssue.getDescription(), equalTo(createdIssue.getDescription()));
    }

    @Test
    public void skipTestIfOpenIssue() throws RemoteException, ServiceException, MalformedURLException {
        skipIfNotFixed(ISSUE_ID);

        testCreateIssue();
    }
}
