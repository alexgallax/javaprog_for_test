package addressbook.tests.contacttests;

import addressbook.model.ContactData;
import addressbook.tests.TestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ContactDeleteTest extends TestBase {

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
    }

    @Test
    public void testContactDelete() {
        app.getContactHelper().selectContact();
        app.getContactHelper().initContactDelete();
        app.getContactHelper().closeAlert();
        app.getNavigationHelper().gotoHomePage();
    }
}
