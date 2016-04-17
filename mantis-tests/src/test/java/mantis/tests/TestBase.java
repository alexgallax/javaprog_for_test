package mantis.tests;

import mantis.appmanager.ApplicationManager;
import mantis.model.MailMessage;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.List;

import static mantis.tests.consts.TestConsts.DEFAULT_TEST_BROWSER;

public class TestBase {

    protected static final ApplicationManager app
            = new ApplicationManager(System.getProperty("browser", DEFAULT_TEST_BROWSER));

    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite
    public void tearDown() {
        app.stop();
    }

    public String findLinkInMessage(List<MailMessage> mailMessages) {
        MailMessage mailMessage = mailMessages.iterator().next();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();

        return regex.getText(mailMessage.text);
    }

    public void skipIfNotFixed(int issueId) {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

    private boolean isIssueOpen(int issueId) {
        return false;
    }
}
