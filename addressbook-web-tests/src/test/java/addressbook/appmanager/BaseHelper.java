package addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class BaseHelper {

    WebDriver wd;

    public BaseHelper(WebDriver wd) {
        this.wd = wd;
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
}
