package addressbook.tests.contacttests;

import addressbook.model.ContactData;
import addressbook.model.GroupData;
import addressbook.tests.TestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ContactCreateTest extends TestBase {

    public static final String GROUP_NAME = "testgroup";

    @BeforeMethod
    public void checkGroupForContactPresent() {
        app.getNavigationHelper().gotoHomePage();
        app.getContactHelper().initContactCreate();

        if (! app.getContactHelper().checkGroupForContact(GROUP_NAME)) {
            app.getNavigationHelper().gotoGroupPage();
            app.getGroupHelper().createGroup(new GroupData(GROUP_NAME, null, null));
        }

        app.getNavigationHelper().gotoHomePage();
    }

    @Test
    public void testContactCreate() {
        app.getContactHelper().createContact(new ContactData("New", "A", "Contact",
                        GROUP_NAME,
                        "unlocated house", "111-11-11",
                        "new.contacta.@testmail.ru",
                        null),
                true);
        app.getNavigationHelper().gotoHomePage();
    }
}
