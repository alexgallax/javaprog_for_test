package addressbook.tests.contacttests;

import addressbook.model.ContactData;
import addressbook.model.GroupData;
import addressbook.model.Items;
import addressbook.tests.TestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreateTest extends TestBase {

    public static final String GROUP_NAME = "testgroup";

    private Items<ContactData> before;
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

        before = app.contact().all();
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
        Items<ContactData> after = app.contact().all();

        assertThat(after.size(), equalTo(before.size() + 1));
        assertThat(after, equalTo(
                before
                        .withAdded(contact
                                .withId(after.stream().mapToInt(c -> c.getId()).max().getAsInt()))));
    }
}
