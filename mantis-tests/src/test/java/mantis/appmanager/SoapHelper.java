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

import static mantis.appmanager.consts.AppConsts.*;

public class SoapHelper {

    private ApplicationManager app;

    public SoapHelper(ApplicationManager app) {
        this.app = app;
    }

    public Set<Project> getProjects() throws MalformedURLException, ServiceException, RemoteException {
        ProjectData[] projects = getMantisConnect()
                .mc_projects_get_user_accessible(app.getProperty(WEB_ADMIN_LOGIN_PROP_NAME),
                        app.getProperty(WEB_ADMIN_PASSWORD_PROP_NAME));

        return Arrays.asList(projects)
                .stream()
                .map((p) -> new Project()
                        .withId(p.getId().intValue())
                        .withName(p.getName()))
                .collect(Collectors.toSet());
    }

    public Issue getIssue(int issueId) throws MalformedURLException, ServiceException, RemoteException {
        IssueData issueData = getMantisConnect().mc_issue_get(app.getProperty(WEB_ADMIN_LOGIN_PROP_NAME),
                app.getProperty(WEB_ADMIN_PASSWORD_PROP_NAME),
                BigInteger.valueOf(issueId));

        return issueDataToIssue(issueData);
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

        BigInteger issueId = mc.mc_issue_add(app.getProperty(WEB_ADMIN_LOGIN_PROP_NAME),
                app.getProperty(WEB_ADMIN_PASSWORD_PROP_NAME),
                issueData);
        IssueData createdIssueData = mc.mc_issue_get(app.getProperty(WEB_ADMIN_LOGIN_PROP_NAME),
                app.getProperty(WEB_ADMIN_PASSWORD_PROP_NAME),
                issueId);

        return issueDataToIssue(createdIssueData);
    }

    private Issue issueDataToIssue(IssueData issueData) {
        return (new Issue()
                .withId(issueData.getId().intValue())
                .withSummary(issueData.getSummary())
                .withDescription(issueData.getDescription())
                .withProject(new Project()
                        .withId(issueData.getProject().getId().intValue())
                        .withName(issueData.getProject().getName()))
                .withResolution(issueData.getResolution().getName()));
    }

    private String[] getCategories(Project project) throws MalformedURLException, ServiceException, RemoteException {
        return getMantisConnect()
                .mc_project_get_categories(app.getProperty(WEB_ADMIN_LOGIN_PROP_NAME),
                        app.getProperty(WEB_ADMIN_PASSWORD_PROP_NAME),
                        BigInteger.valueOf(project.getId()));
    }

    private MantisConnectPortType getMantisConnect() throws MalformedURLException, ServiceException {
        return new MantisConnectLocator()
                .getMantisConnectPort(new URL(app.getProperty(MANTIS_SOAP_URL_PROP)));
    }
}
