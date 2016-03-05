package addressbook.tests.contacttests;

import addressbook.tests.TestBase;
import org.testng.annotations.Test;

public class ContactDeleteTest extends TestBase {

    @Test
    public void testContactDelete() {
        app.getNavigationHelper().gotoHomePage();
        app.getContactHelper().selectContact();
        app.getContactHelper().initContactDelete();
        app.getContactHelper().closeAlert();
        app.getNavigationHelper().gotoHomePage();
    }
}
