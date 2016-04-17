package mantis.tests;

import mantis.model.Project;
import org.testng.annotations.Test;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

public class ProjectTests extends TestBase {

    @Test
    public void testGetProjects() throws RemoteException, ServiceException, MalformedURLException {
        Set<Project> projects = app.soap().getProjects();

        System.out.println("Number of Projects: " + projects.size());
        System.out.println("Projects:\n" + projects);
    }
}
