package addressbook.appmanager;

import addressbook.model.ContactData;
import addressbook.model.Items;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends BaseHelper {

    private Items<ContactData> contactCache = null;

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
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("phone2"), contactData.getHomePhone2());
        type(By.name("email"), contactData.getEmail());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email3"), contactData.getEmail3());
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

    public void initContactDetailsById(int id) {
        wd.findElement(By.cssSelector("a[href='view.php?id=" + id + "']")).click();
    }

    public ContactData initEditFormInfo(ContactData contact) {
        initContactModifyById(contact.getId());

        String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
        String middleName = wd.findElement(By.name("middlename")).getAttribute("value");
        String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
        String nickname = wd.findElement(By.name("nickname")).getAttribute("value");

        String company = wd.findElement(By.name("company")).getAttribute("value");
        String title = wd.findElement(By.name("title")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");

        String homePhone = wd.findElement(By.name("home")).getAttribute("value");
        String mobilePhone = wd.findElement(By.name("mobile")).getAttribute("value");
        String workPhone = wd.findElement(By.name("work")).getAttribute("value");
        String fax = wd.findElement(By.name("fax")).getAttribute("value");

        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        String homepage = wd.findElement(By.name("homepage")).getAttribute("value");

        String bday = wd.findElement(By.name("bday")).getAttribute("value");
        String bmonth = wd.findElement(By.name("bmonth")).getAttribute("value");
        String byear = wd.findElement(By.name("byear")).getAttribute("value");

        String aday = wd.findElement(By.name("aday")).getAttribute("value");
        String amonth = wd.findElement(By.name("amonth")).getAttribute("value");
        String ayear = wd.findElement(By.name("ayear")).getAttribute("value");

        String address2 = wd.findElement(By.name("address2")).getAttribute("value");
        String homePhone2 = wd.findElement(By.name("phone2")).getAttribute("value");
        String notes = wd.findElement(By.name("notes")).getAttribute("value");

        return new ContactData()
                .withFirstName(firstName)
                .withMiddleName(middleName)
                .withLastName(lastName)
                .withNickname(nickname)
                .withCompany(company)
                .withTitle(title)
                .withAddress(address)
                .withHomePhone(homePhone)
                .withMobilePhone(mobilePhone)
                .withWorkPhone(workPhone)
                .withFax(fax)
                .withEmail(email)
                .withEmail2(email2)
                .withEmail3(email3)
                .withHomepage(homepage)
                .withBday(bday)
                .withBmonth(bmonth)
                .withByear(byear)
                .withAday(aday)
                .withAmonth(amonth)
                .withAyear(ayear)
                .withAddress2(address2)
                .withHomePhone2(homePhone2)
                .withNotes(notes);
    }

    public ContactData initDetailsFormInfo(ContactData contact) {
        initContactDetailsById(contact.getId());

        return new ContactData()
                .withDetails(wd.findElement(By.cssSelector("div[id='content']")).getText());
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
        contactCache = null;
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        initContactDelete();
        closeAlert();
        contactCache = null;
    }

    public void modify(ContactData contact) {
        initContactModifyById(contact.getId());
        fillContactForms(contact, false);
        submitContactModify();
        contactCache = null;
    }

    public void noEdit(ContactData contact) {
        initContactModifyById(contact.getId());
        submitContactModify();
        contactCache = null;
    }

    public void edit(ContactData contact, String text) {
        initContactModifyById(contact.getId());
        addTextToContactForms(text);
        submitContactModify();
        contactCache = null;
    }

    public void clear(ContactData contact) {
        initContactModifyById(contact.getId());
        clearContactForms();
        submitContactModify();
        contactCache = null;
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
                    .withMobilePhone(mobile)
                    .withEmail(email);

            contacts.add(contact);
        }

        return contacts;
    }

    public Items<ContactData> all() {
        if (contactCache != null) {
            return new Items<>(contactCache);
        }

        contactCache = new Items<>();
        List<WebElement> elements = wd.findElements(By.name(("entry")));

        for (WebElement element : elements) {
            String lastName = element.findElements(By.tagName("td")).get(1).getText();
            String firstName = element.findElements(By.tagName("td")).get(2).getText();
            String address = element.findElements(By.tagName("td")).get(3).getText();
            String allEmails = element.findElements(By.tagName("td")).get(4).getText();
            String allPhones = element.findElements(By.tagName("td")).get(5).getText();

            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));

            contactCache.add(new ContactData()
                    .withId(id)
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .withAddress(address)
                    .withAllEmails(allEmails)
                    .withAllPhones(allPhones));
        }

        return new Items<>(contactCache);
    }
}
