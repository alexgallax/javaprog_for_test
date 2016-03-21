package addressbook.tests.contacttests;

import addressbook.model.ContactData;
import addressbook.model.Items;
import addressbook.tests.TestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static addressbook.tests.consts.TestConsts.TEXT_ADDED_TO_FORM;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModifyTest extends TestBase {

    private Items<ContactData> before;
    private ContactData modifiedContact;
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
                    .withMobile("111-11-11")
                    .withEmail("new.contacta.@testmail.ru"));
            app.goTo().gotoHomePage();
        }

        before = app.contact().all();

        modifiedContact = before.iterator().next();
    }

    @Test
    public void testContactModifyNoEdit() {
        contact = modifiedContact;

        app.contact().noEdit(contact);
        app.goTo().gotoHomePage();

        makeChecks();
    }

    @Test
    public void testContactModifyFillForms() {
        contact = new ContactData()
                .withId(modifiedContact.getId())
                .withFirstName("New")
                .withMiddleName("A")
                .withLastName("Contact")
                .withAddress("unlocated house")
                .withMobile("111-11-11")
                .withEmail("new.contacta.@testmail.ru");

        app.contact().modify(contact);
        app.goTo().gotoHomePage();

        makeChecks();
    }

    @Test
    public void testContactModifyEditForms() {
        contact = new ContactData()
                .withId(modifiedContact.getId())
                .withFirstName(modifiedContact.getFirstName() + TEXT_ADDED_TO_FORM)
                .withLastName(modifiedContact.getLastName() + TEXT_ADDED_TO_FORM)
                .withAddress(modifiedContact.getAddress() + TEXT_ADDED_TO_FORM)
                .withMobile(modifiedContact.getMobile() + TEXT_ADDED_TO_FORM)
                .withEmail(modifiedContact.getEmail() + TEXT_ADDED_TO_FORM);

        app.contact().edit(contact, TEXT_ADDED_TO_FORM);
        app.goTo().gotoHomePage();

        makeChecks();
    }

    @Test
    public void testContactModifyClearForms() {
        contact = new ContactData()
                .withId(modifiedContact.getId())
                .withFirstName("")
                .withMiddleName("")
                .withLastName("")
                .withAddress("")
                .withMobile("")
                .withEmail("");

        app.contact().clear(contact);
        app.goTo().gotoHomePage();

        makeChecks();
    }

    private void makeChecks() {
        Items<ContactData> after = app.contact().all();

        assertThat(after.size(), equalTo(before.size()));
        assertThat(after, equalTo(
                before
                        .without(modifiedContact)
                        .withAdded(contact)));
    }
}
