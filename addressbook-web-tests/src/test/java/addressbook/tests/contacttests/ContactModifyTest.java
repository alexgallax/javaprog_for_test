package addressbook.tests.contacttests;

import addressbook.model.ContactData;
import addressbook.tests.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

import static addressbook.tests.consts.TestConsts.TEXT_ADDED_TO_FORM;

public class ContactModifyTest extends TestBase {

    private List<ContactData> before;
    private int contactIndex;
    private ContactData contact;

    @BeforeMethod
    public void gotoContactPageAndCheckInit() {
        app.getNavigationHelper().gotoHomePage();

        if (! app.getContactHelper().isContactsFound()) {
            app.getContactHelper().createContact(new ContactData("New", "A", "Contact",
                    null,
                    "unlocated house", "111-11-11",
                    "new.contacta.@testmail.ru",
                    null));
            app.getNavigationHelper().gotoHomePage();
        }

        before = app.getContactHelper().getContactList();
    }

    @Test
    public void testContactModifyNoEdit() {
        contactIndex = 0;
        contact = before.get(contactIndex);

        app.getContactHelper().initContactModify(contactIndex);
        app.getContactHelper().submitContactModify();
        app.getNavigationHelper().gotoHomePage();

        makeChecks();
    }

    @Test
    public void testContactModifyFillForms() {
        contactIndex = before.size() - 1;
        contact = new ContactData(before.get(contactIndex).getId(), "New", "A", "Contact",
                null,
                "unlocated house", "111-11-11",
                "new.contacta.@testmail.ru",
                null);

        app.getContactHelper().initContactModify(contactIndex);
        app.getContactHelper().fillContactForms(contact, false);
        app.getContactHelper().submitContactModify();
        app.getNavigationHelper().gotoHomePage();

        makeChecks();
    }

    @Test
    public void testContactModifyEditForms() {
        contactIndex = before.size() - 1;
        contact = new ContactData(before.get(contactIndex).getId(),
                before.get(contactIndex).getFirstName() + TEXT_ADDED_TO_FORM,
                null,
                before.get(contactIndex).getLastName() + TEXT_ADDED_TO_FORM,
                null,
                before.get(contactIndex).getAddress() + TEXT_ADDED_TO_FORM,
                before.get(contactIndex).getMobile() + TEXT_ADDED_TO_FORM,
                before.get(contactIndex).getEmail() + TEXT_ADDED_TO_FORM,
                null);

        app.getContactHelper().initContactModify(contactIndex);
        app.getContactHelper().addTextToContactForms(TEXT_ADDED_TO_FORM);
        app.getContactHelper().submitContactModify();
        app.getNavigationHelper().gotoHomePage();

        makeChecks();
    }

    @Test
    public void testContactModifyClearForms() {
        contactIndex = 0;
        contact = new ContactData(before.get(contactIndex).getId(), "", "", "", "", "", "", "", "");

        app.getContactHelper().initContactModify(contactIndex);
        app.getContactHelper().clearContactForms();
        app.getContactHelper().submitContactModify();
        app.getNavigationHelper().gotoHomePage();

        makeChecks();
    }

    private void makeChecks() {
        List<ContactData> after = app.getContactHelper().getContactList();

        Assert.assertEquals(after.size(), before.size());

        before.remove(contactIndex);
        before.add(contact);

        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(after, before);
    }
}
