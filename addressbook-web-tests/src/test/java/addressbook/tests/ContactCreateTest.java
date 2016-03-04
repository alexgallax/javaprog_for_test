package addressbook.tests;

import addressbook.model.ContactData;
import org.testng.annotations.Test;

public class ContactCreateTest extends TestBase {

    @Test
    public void testContactCreate() {
        app.initContactCreate();
        app.fillContactForms(new ContactData("New", "A", "Contact",
                "unlocated house", "111-11-11",
                "new.contacta.@testmail.ru",
                "new contact"));
        app.submitContactCreate();
        app.returnToHomePage();
    }
}
