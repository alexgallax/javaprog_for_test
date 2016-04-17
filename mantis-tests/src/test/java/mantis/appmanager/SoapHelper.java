package mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import mantis.model.Issue;
import mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapHelper {

    public Set<Project> getProjects() throws MalformedURLException, ServiceException, RemoteException {
        ProjectData[] projects = getMantisConnect()
                .mc_projects_get_user_accessible("administrator", "root");

        return Arrays.asList(projects)
                .stream()
                .map((p) -> new Project()
                        .withId(p.getId().intValue())
                        .withName(p.getName()))
                .collect(Collectors.toSet());
    }

    public Issue createIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
        String[] categories = getCategories(issue.getProject());

        IssueData issueData = new IssueData();
        issueData.setSummary(issue.getSummary());
        issueData.setDescription(issue.getDescription());
        issueData.setProject(
                new ObjectRef(BigInteger.valueOf(issue.getProject().getId()),
                        issue.getProject().getName()));
        issueData.setCategory(categories[0]);

        BigInteger issueId = mc.mc_issue_add("administrator", "root", issueData);
        IssueData createdIssueData = mc.mc_issue_get("administrator", "root", issueId);

        return (new Issue()
                .withId(createdIssueData.getId().intValue())
                .withSummary(createdIssueData.getSummary())
                .withDescription(createdIssueData.getDescription())
                .withProject(new Project()
                        .withId(createdIssueData.getProject().getId().intValue())
                        .withName(createdIssueData.getProject().getName())));
    }

    private String[] getCategories(Project project) throws MalformedURLException, ServiceException, RemoteException {
        return getMantisConnect()
                .mc_project_get_categories("administrator", "root", BigInteger.valueOf(project.getId()));
    }

    private MantisConnectPortType getMantisConnect() throws MalformedURLException, ServiceException {
        return new MantisConnectLocator()
                .getMantisConnectPort(new URL("http://localhost/mantisbt-1.2.19/api/soap/mantisconnect.php"));
    }
}
