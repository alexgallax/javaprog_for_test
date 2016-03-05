package addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseHelper {

    FirefoxDriver wd;

    public BaseHelper(FirefoxDriver wd) {
        this.wd = wd;
    }

    protected boolean checkSelect(By locator) {
        return wd.findElement(locator).isSelected();
    }

    protected void click(By locator) {
        wd.findElement(locator).click();
    }

    protected void type(By locator, String text) {
        clearForm(locator);
        addTextToForm(locator, text);
    }

    protected void clearForm(By locator) {
        wd.findElement(locator).click();
        wd.findElement(locator).clear();
    }

    protected void addTextToForm(By locator, String text) {
        String formText = wd.findElement(locator).getText();
        String newFormText = formText + text;

        wd.findElement(locator).sendKeys(newFormText);
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
}
