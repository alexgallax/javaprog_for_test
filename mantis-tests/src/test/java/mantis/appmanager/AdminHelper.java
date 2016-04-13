package mantis.appmanager;

import mantis.model.UserData;
import org.openqa.selenium.By;

import static mantis.appmanager.consts.AppConsts.WEB_ADMIN_LOGIN_PROP_NAME;
import static mantis.appmanager.consts.AppConsts.WEB_ADMIN_PASSWORD_PROP_NAME;

public class AdminHelper extends BaseHelper {

    public AdminHelper(ApplicationManager app) {
        super(app);
    }

    public void login() {
        super.login(app.getProperty(WEB_ADMIN_LOGIN_PROP_NAME), app.getProperty(WEB_ADMIN_PASSWORD_PROP_NAME));
    }

    public void manage() {
        click(By.linkText("Manage"));
    }

    public void manageUsers() {
        click(By.linkText("Manage Users"));
    }

    public void selectUser(UserData user) {
        click(By.linkText(user.getUsername()));
    }

    public void resetPassword() {
        click(By.cssSelector("input[value='Reset Password']"));
    }
}
