package addressbook.appmanager;

import addressbook.model.ContactData;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

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
            if (contactData.getGroup() != null) {
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
            }
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

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void initContactCreate() {
        click(By.linkText("add new"));
    }

    public void initContactDelete() {
        click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    }

    public void initContactModify(int index) {
        wd.findElements(By.xpath("//a/img[@title='Edit']")).get(index).click();
    }

    public boolean isContactsFound() {
        try {
            String contactsNumber = wd.findElement(By.id("search_count")).getText();
            return (! contactsNumber.equals("0"));
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void create(ContactData contact) {
        initContactCreate();
        fillContactForms(contact, true);
        submitContactCreate();
    }

    public void delete(int index) {
        selectContact(index);
        initContactDelete();
        closeAlert();
    }

    public void modify(int index, ContactData contact) {
        initContactModify(index);
        fillContactForms(contact, false);
        submitContactModify();
    }

    public void noEdit(int index) {
        initContactModify(index);
        submitContactModify();
    }

    public void edit(int index, String text) {
        initContactModify(index);
        addTextToContactForms(text);
        submitContactModify();
    }

    public void clear(int index) {
        initContactModify(index);
        clearContactForms();
        submitContactModify();
    }

    public boolean checkGroupForContact(String groupName) {
        try {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(groupName);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public List<ContactData> list() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> elements = wd.findElements(By.name(("entry")));

        for (WebElement element : elements) {
            String lastName = element.findElements(By.tagName("td")).get(1).getText();
            String firstName = element.findElements(By.tagName("td")).get(2).getText();
            String address = element.findElements(By.tagName("td")).get(3).getText();
            String email = element.findElements(By.tagName("td")).get(4).getText();
            String mobile = element.findElements(By.tagName("td")).get(5).getText();

            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));

            ContactData contact = new ContactData(id, firstName, null, lastName,
                    null,
                    address, mobile,
                    email,
                    null);

            contacts.add(contact);
        }

        return contacts;
    }
}
