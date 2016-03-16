package addressbook.tests.contacttests;

import addressbook.model.ContactData;
import addressbook.tests.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class ContactDeleteTest extends TestBase {

    private List<ContactData> before;
    private int contactIndex;

    @BeforeMethod
    public void gotoContactPageAndCheckInit() {
        app.getNavigationHelper().gotoHomePage();

        if (! app.getContactHelper().isContactsFound()) {
            app.getContactHelper().createContact(new ContactData("New", "A", "Contact",
                    null,
                    "unlocated house", "111-11-11",
                    "new.contacta.@testmail.ru",
                    null));
            app.getNavigationHelper().gotoHomePage();
        }

        before = app.getContactHelper().getContactList();
    }

    @Test
    public void testContactDelete() {
        contactIndex = before.size() - 1;

        app.getContactHelper().selectContact(contactIndex);
        app.getContactHelper().initContactDelete();
        app.getContactHelper().closeAlert();
        app.getNavigationHelper().gotoHomePage();

        makeChecks();
    }

    private void makeChecks() {
        List<ContactData> after = app.getContactHelper().getContactList();

        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(contactIndex);

        Assert.assertEquals(after, before);
    }
}
