package addressbook.tests.contacttests;

import addressbook.model.ContactData;
import addressbook.tests.TestBase;
import org.testng.annotations.Test;

public class ContactCreateTest extends TestBase {

    @Test
    public void testContactCreate() {
        app.getContactHelper().initContactCreate();
        app.getContactHelper().fillContactForms(new ContactData("New", "A", "Contact",
                "testgroup",
                "unlocated house", "111-11-11",
                "new.contacta.@testmail.ru",
                null),
                true);
        app.getContactHelper().submitContactCreate();
        app.getNavigationHelper().gotoHomePage();
    }
}
