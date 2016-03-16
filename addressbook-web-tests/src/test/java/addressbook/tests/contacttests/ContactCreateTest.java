package addressbook.tests.contacttests;

import addressbook.model.ContactData;
import addressbook.model.GroupData;
import addressbook.tests.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

public class ContactCreateTest extends TestBase {

    public static final String GROUP_NAME = "testgroup";

    private List<ContactData> before;
    private ContactData contact;

    @BeforeMethod
    public void checkGroupForContactPresent() {
        app.getNavigationHelper().gotoHomePage();
        app.getContactHelper().initContactCreate();

        if (! app.getContactHelper().checkGroupForContact(GROUP_NAME)) {
            app.getNavigationHelper().gotoGroupPage();
            app.getGroupHelper().createGroup(new GroupData(GROUP_NAME, null, null));
        }

        app.getNavigationHelper().gotoHomePage();

        before = app.getContactHelper().getContactList();
    }

    @Test
    public void testContactCreate() {
        contact = new ContactData("New", "A", "Contact",
                GROUP_NAME,
                "unlocated house", "111-11-11",
                "new.contacta.@testmail.ru",
                null);

        app.getContactHelper().createContact(contact);
        app.getNavigationHelper().gotoHomePage();

        makeChecks();
    }

    private void makeChecks() {
        List<ContactData> after = app.getContactHelper().getContactList();

        Assert.assertEquals(after.size(), before.size() + 1);

        before.add(contact);

        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(after, before);
    }
}
