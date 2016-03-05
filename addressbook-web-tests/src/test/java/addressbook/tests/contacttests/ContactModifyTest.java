package addressbook.tests.contacttests;

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
