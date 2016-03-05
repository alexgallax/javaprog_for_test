package addressbook.tests.contacttests;

import addressbook.tests.TestBase;
import org.testng.annotations.Test;

public class ContactModifyTest extends TestBase {

    @Test
    public void testContactModify() {
        app.getNavigationHelper().gotoHomePage();
        app.getContactHelper().initContactModify();
        app.getContactHelper().submitContactModify();
        app.getNavigationHelper().gotoHomePage();
    }
}
