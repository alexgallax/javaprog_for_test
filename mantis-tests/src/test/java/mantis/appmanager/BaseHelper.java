package mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import static mantis.appmanager.consts.AppConsts.*;

public class BaseHelper {

    protected final ApplicationManager app;
    protected WebDriver wd;

    public BaseHelper(ApplicationManager app) {
        this.app = app;
        this.wd = app.getDriver();
    }

    protected boolean checkSelect(By locator) {
        return wd.findElement(locator).isSelected();
    }

    protected void click(By locator) {
        wd.findElement(locator).click();
    }

    protected void type(By locator, String text) {
        click(locator);

        if (text != null) {
            String existedText = wd.findElement(locator).getAttribute("value");

            if (! existedText.equals(text)) {
                clearForm(locator);
                wd.findElement(locator).sendKeys(text);
            }
        }
    }

    protected void clearForm(By locator) {
        wd.findElement(locator).click();
        wd.findElement(locator).clear();
    }

    protected void addTextToForm(By locator, String text) {
        click(locator);

        wd.findElement(locator).sendKeys(text);
    }

    public void closeAlert() {
        wd.switchTo().alert().accept();
    }

    public boolean isAlertPresent() {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    protected boolean isElementFound(By locator) {
        try {
            wd.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected void login(String username, String password) {
        wd.get(app.getProperty(WEB_BASE_URL_PROP) + LOGIN_PAGE);
        type(By.name("username"), username);
        type(By.name("password"), password);
        click(By.cssSelector("input[value='Login']"));
    }
}
