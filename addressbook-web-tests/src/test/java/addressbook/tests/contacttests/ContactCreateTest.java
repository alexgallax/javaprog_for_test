package addressbook.tests.contacttests;

import addressbook.model.ContactData;
import addressbook.model.GroupData;
import addressbook.tests.TestBase;
import org.testng.Assert;
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
        app.goTo().gotoHomePage();
        app.contact().initContactCreate();

        if (! app.contact().checkGroupForContact(GROUP_NAME)) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName(GROUP_NAME));
        }

        app.goTo().gotoHomePage();

        before = app.contact().list();
    }

    @Test
    public void testContactCreate() {
        contact = new ContactData()
                .withFirstName("New")
                .withMiddleName("A")
                .withLastName("Contact")
                .withGroup(GROUP_NAME)
                .withAddress("unlocated house")
                .withMobile("111-11-11")
                .withEmail("new.contacta.@testmail.ru");

        app.contact().create(contact);
        app.goTo().gotoHomePage();

        makeChecks();
    }

    private void makeChecks() {
        List<ContactData> after = app.contact().list();

        Assert.assertEquals(after.size(), before.size() + 1);

        before.add(contact);

        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(after, before);
    }
}
