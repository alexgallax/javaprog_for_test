package addressbook.tests.contacttests;

import addressbook.model.ContactData;
import addressbook.tests.TestBase;
import org.testng.annotations.Test;

import static addressbook.tests.consts.TestConsts.TEXT_ADDED_TO_FORM;

public class ContactModifyTest extends TestBase {

    @Test
    public void testContactModifyNoEdit() {
        app.getNavigationHelper().gotoHomePage();
        app.getContactHelper().initContactModify();
        app.getContactHelper().submitContactModify();
        app.getNavigationHelper().gotoHomePage();
    }

    @Test
    public void testContactModifyFillForms() {
        app.getNavigationHelper().gotoHomePage();
        app.getContactHelper().initContactModify();
        app.getContactHelper().fillContactForms(new ContactData("New", "A", "Contact",
                null,
                "unlocated house", "111-11-11",
                "new.contacta.@testmail.ru",
                null),
                false);
        app.getContactHelper().submitContactModify();
        app.getNavigationHelper().gotoHomePage();
    }

    @Test
    public void testContactModifyEditForms() {
        app.getNavigationHelper().gotoHomePage();
        app.getContactHelper().initContactModify();
        app.getContactHelper().addTextToContactForms(TEXT_ADDED_TO_FORM);
        app.getContactHelper().submitContactModify();
        app.getNavigationHelper().gotoHomePage();
    }

    @Test
    public void testContactModifyClearForms() {
        app.getNavigationHelper().gotoHomePage();
        app.getContactHelper().initContactModify();
        app.getContactHelper().clearContactForms();
        app.getContactHelper().submitContactModify();
        app.getNavigationHelper().gotoHomePage();
    }
}
