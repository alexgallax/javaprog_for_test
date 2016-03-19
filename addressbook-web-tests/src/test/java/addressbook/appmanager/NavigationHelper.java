package addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends BaseHelper {

    public NavigationHelper(WebDriver wd) {
        super(wd);
    }

    public void login(String username, String password) {
        type(By.name("user"), username);
        type(By.name("pass"), password);
        click(By.xpath("//form[@id='LoginForm']/input[3]"));
    }

    public void groupPage() {
        if (isElementFound(By.tagName("h1"))
                && wd.findElement(By.tagName("h1")).getText().equals("Groups")
                && isElementFound(By.name("new"))) {
            return;
        }

        click(By.linkText("groups"));
    }

    public void gotoHomePage() {
        if (isElementFound(By.id("maintable"))) {
            return;
        }

        click(By.linkText("home"));
    }
}
