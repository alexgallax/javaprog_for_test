package mantis.appmanager;

import org.openqa.selenium.By;

import static mantis.appmanager.consts.AppConsts.SIGNUP_PAGE;
import static mantis.appmanager.consts.AppConsts.WEB_BASE_URL_PROP;

public class UserHelper extends BaseHelper {

    public UserHelper(ApplicationManager app) {
        super(app);
    }

    public void login(String username, String password) {
        super.login(username, password);
    }

    public void registrate(String username, String email) {
        wd.get(app.getProperty(WEB_BASE_URL_PROP) + SIGNUP_PAGE);
        type(By.name("username"), username);
        type(By.name("email"), email);
        click(By.cssSelector("input[value='Signup']"));
    }

    public void setPassword(String link, String password) {
        wd.get(link);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.cssSelector("input[value='Update User']"));
    }
}
