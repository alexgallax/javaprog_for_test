package addressbook.tests.contacttests;

import addressbook.model.ContactData;
import addressbook.tests.TestBase;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class ContactDeleteTest extends TestBase {

    private List<ContactData> before;
    private int contactIndex;

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

        before = app.contact().list();
    }

    @Test
    public void testContactDelete() {
        contactIndex = before.size() - 1;

        app.contact().delete(contactIndex);
        app.goTo().gotoHomePage();

        makeChecks();
    }

    private void makeChecks() {
        List<ContactData> after = app.contact().list();

        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(contactIndex);

        Assert.assertEquals(after, before);
    }
}
