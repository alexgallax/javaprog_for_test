package addressbook.appmanager;

import addressbook.model.ContactData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class ContactHelper extends BaseHelper {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitContactCreate() {
        click(By.name("submit"));
    }

    public void submitContactModify() {
        click(By.name("update"));
    }

    public void fillContactForms(ContactData contactData, boolean create) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("middlename"), contactData.getMiddleName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("address"), contactData.getAddress());
        type(By.name("mobile"), contactData.getMobile());
        type(By.name("email"), contactData.getEmail());
        type(By.name("notes"), contactData.getNotes());

        if (create) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementFound(By.name("new_group")));
        }
    }

    public void clearContactForms() {
        clearForm(By.name("firstname"));
        clearForm(By.name("middlename"));
        clearForm(By.name("lastname"));
        clearForm(By.name("address"));
        clearForm(By.name("mobile"));
        clearForm(By.name("email"));
        clearForm(By.name("notes"));
    }

    public void addTextToContactForms(String text) {
        addTextToForm(By.name("firstname"), text);
        addTextToForm(By.name("middlename"), text);
        addTextToForm(By.name("lastname"), text);
        addTextToForm(By.name("address"), text);
        addTextToForm(By.name("mobile"), text);
        addTextToForm(By.name("email"), text);
        addTextToForm(By.name("notes"), text);
    }

    public void selectContact() {
        if (!checkSelect(By.name("selected[]"))) {
            click(By.name("selected[]"));
        }
    }

    public void initContactCreate() {
        click(By.linkText("add new"));
    }

    public void initContactDelete() {
        click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    }

    public void initContactModify() {
        click(By.xpath("//table[@id='maintable']/tbody/tr[2]/td[8]/a/img"));
    }
}
