package mantis.tests;

import mantis.appmanager.HttpSession;
import mantis.model.MailMessage;
import mantis.model.UserData;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ResetPasswordTest extends TestBase {

    private static final String NEW_CONFIG_PATH = "src/test/resources/config_inc.php";
    private static final String ORIGINAL_CONFIG_PATH = "config_inc.php";
    private static final String BACKUP_CONFIG_PATH = "config_inc.php.bak";

    private static final String DEFAULT_USER = "user";
    private static final String DEFAULT_PASSWORD = "password";
    private static final String DEFAULT_EMAIL = "user@localhost.localdomain";

    private static final int MAIL_TIMEOUT = 10000;

    private UserData user;
    private String newPassword;

    @BeforeMethod
    public void setInit() throws IOException {
        app.ftp().upload(new File(NEW_CONFIG_PATH), ORIGINAL_CONFIG_PATH, BACKUP_CONFIG_PATH);
        app.mail().start();

        if (app.db().notAdminUsers().size() == 0) {
            app.user().registrate(DEFAULT_USER, DEFAULT_EMAIL);
            List<MailMessage> mailMessages = app.mail().waitForMail(MAIL_TIMEOUT);
            String confirmationLink = findLinkInMessage(mailMessages);
            app.user().setPassword(confirmationLink, DEFAULT_PASSWORD);

            app.mail().clear();
        }

        user = app.db().notAdminUsers().iterator().next();

        app.admin().login();
        app.admin().manage();
        app.admin().manageUsers();
    }

    @Test
    public void testResetPassport() {
        app.admin().selectUser(user);
        app.admin().resetPassword();

        List<MailMessage> mailMessages = app.mail().waitForMail(MAIL_TIMEOUT);
        String confirmationLink = findLinkInMessage(mailMessages);
        newPassword = String.format(DEFAULT_PASSWORD + "%s", System.currentTimeMillis());
        app.user().setPassword(confirmationLink, newPassword);
    }

    @AfterMethod(alwaysRun = true)
    public void makeChecks() throws IOException {
        app.mail().stop();
        app.ftp().restore(BACKUP_CONFIG_PATH, ORIGINAL_CONFIG_PATH);

        HttpSession session = app.newSession();

        assertTrue(session.login(user.getUsername(), newPassword));
        assertTrue(session.isLoggedInAs(user.getUsername()));
    }
}
