package addressbook.tests.contacttests;

import addressbook.model.ContactData;
import addressbook.model.Items;
import addressbook.tests.TestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeleteTest extends TestBase {

    private Items<ContactData> before;
    private ContactData contact;

    @BeforeMethod
    public void gotoContactPageAndCheckInit() {
        app.goTo().gotoHomePage();

        if (! app.contact().isContactsFound()) {
            app.contact().create(new ContactData()
                    .withFirstName("New")
                    .withMiddleName("A")
                    .withLastName("Contact")
                    .withAddress("unlocated house")
                    .withMobilePhone("111-11-11")
                    .withEmail("new.contacta.@testmail.ru"));
            app.goTo().gotoHomePage();
        }

        before = app.db().contacts();
    }

    @Test
    public void testContactDelete() {
        contact = before.iterator().next();

        app.contact().delete(contact);
        app.goTo().gotoHomePage();

        makeChecks();
    }

    private void makeChecks() {
        assertThat(app.contact().count(), equalTo(before.size() - 1));

        Items<ContactData> after = app.db().contacts();
        assertThat(after, equalTo(
                before
                        .without(contact)));
    }
}
