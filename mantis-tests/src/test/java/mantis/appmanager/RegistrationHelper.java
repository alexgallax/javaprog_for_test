package mantis.appmanager;

import org.openqa.selenium.By;

import static mantis.appmanager.AppConsts.SIGNUP_PAGE;
import static mantis.appmanager.AppConsts.WEB_BASE_URL_PROP;

public class RegistrationHelper extends BaseHelper {

    public RegistrationHelper(ApplicationManager app) {
        super(app);
    }

    public void start(String username, String email) {
        wd.get(app.getProperty(WEB_BASE_URL_PROP) + SIGNUP_PAGE);
        type(By.name("username"), username);
        type(By.name("password"), email);
        click(By.cssSelector("input[value='Signup']"));
    }

    public void finish(String confirmationLink, String password) {
        wd.get(confirmationLink);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.cssSelector("input[value='Update_User']"));
    }
}
