package addressbook.tests;

import addressbook.model.ContactData;
import org.testng.annotations.Test;

public class ContactCreateTest extends TestBase {

    @Test
    public void testContactCreate() {
        app.getContactHelper().initContactCreate();
        app.getContactHelper().fillContactForms(new ContactData("New", "A", "Contact",
                "unlocated house", "111-11-11",
                "new.contacta.@testmail.ru",
                "new contact"));
        app.getContactHelper().submitContactCreate();
        app.getNavigationHelper().gotoHomePage();
    }
}
