package addressbook.appmanager;

import addressbook.model.ContactData;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void initContactCreate() {
        click(By.linkText("add new"));
    }

    public void initContactDelete() {
        click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    }

    public void initContactModifyById(int id) {
        wd.findElement(By.cssSelector("a[href='edit.php?id=" + id + "']")).click();
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

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        initContactDelete();
        closeAlert();
    }

    public void modify(ContactData contact) {
        initContactModifyById(contact.getId());
        fillContactForms(contact, false);
        submitContactModify();
    }

    public void noEdit(ContactData contact) {
        initContactModifyById(contact.getId());
        submitContactModify();
    }

    public void edit(ContactData contact, String text) {
        initContactModifyById(contact.getId());
        addTextToContactForms(text);
        submitContactModify();
    }

    public void clear(ContactData contact) {
        initContactModifyById(contact.getId());
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

            ContactData contact = new ContactData()
                    .withId(id)
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .withAddress(address)
                    .withMobile(mobile)
                    .withEmail(email);

            contacts.add(contact);
        }

        return contacts;
    }

    public Set<ContactData> all() {
        Set<ContactData> contacts = new HashSet<>();
        List<WebElement> elements = wd.findElements(By.name(("entry")));

        for (WebElement element : elements) {
            String lastName = element.findElements(By.tagName("td")).get(1).getText();
            String firstName = element.findElements(By.tagName("td")).get(2).getText();
            String address = element.findElements(By.tagName("td")).get(3).getText();
            String email = element.findElements(By.tagName("td")).get(4).getText();
            String mobile = element.findElements(By.tagName("td")).get(5).getText();

            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));

            contacts.add(new ContactData()
                    .withId(id)
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .withAddress(address)
                    .withMobile(mobile)
                    .withEmail(email));
        }

        return contacts;
    }
}
