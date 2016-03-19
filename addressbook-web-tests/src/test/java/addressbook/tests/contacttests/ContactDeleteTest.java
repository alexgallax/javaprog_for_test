package addressbook.tests.contacttests;

import addressbook.model.ContactData;
import addressbook.tests.TestBase;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Set;

public class ContactDeleteTest extends TestBase {

    private Set<ContactData> before;
    private ContactData contact;

    @BeforeMethod
    public void gotoContactPageAndCheckInit() {
        app.goTo().gotoHomePage();

        if (! app.contact().isContactsFound()) {
            app.contact().create(new ContactData()
                    .withFirstName("New")
                    .withMiddleName("A")
                    .withLastName("Contact")
                    .withAddress("unlocated house")
                    .withMobile("111-11-11")
                    .withEmail("new.contacta.@testmail.ru"));
            app.goTo().gotoHomePage();
        }

        before = app.contact().all();
    }

    @Test
    public void testContactDelete() {
        contact = before.iterator().next();

        app.contact().delete(contact);
        app.goTo().gotoHomePage();

        makeChecks();
    }

    private void makeChecks() {
        Set<ContactData> after = app.contact().all();

        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(contact);

        Assert.assertEquals(after, before);
    }
}
