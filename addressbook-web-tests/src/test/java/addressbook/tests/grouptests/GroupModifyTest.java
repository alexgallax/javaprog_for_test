package addressbook.tests.grouptests;

import addressbook.tests.TestBase;
import org.testng.annotations.Test;

import static addressbook.tests.consts.TestConsts.*;

public class GroupModifyTest extends TestBase {

    @Test
    public void testGroupModifyNoEdit() {
        app.getNavigationHelper().gotoGroupPage();
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().initGroupModify();
        app.getGroupHelper().submitGroupModify();
        app.getGroupHelper().returnToGroupPage();
    }

    @Test
    public void testGroupModifyEditForms() {
        app.getNavigationHelper().gotoGroupPage();
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().initGroupModify();
        app.getGroupHelper().addTextToGroupForms(TEXT_ADDED_TO_FORM);
        app.getGroupHelper().submitGroupModify();
        app.getGroupHelper().returnToGroupPage();
    }

    @Test
    public void testGroupModifyClearForms() {
        app.getNavigationHelper().gotoGroupPage();
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().initGroupModify();
        app.getGroupHelper().clearGroupForms();
        app.getGroupHelper().submitGroupModify();
        app.getGroupHelper().returnToGroupPage();
    }
}
