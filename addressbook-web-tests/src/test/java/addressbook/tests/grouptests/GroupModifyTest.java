package addressbook.tests.grouptests;

import addressbook.model.GroupData;
import addressbook.tests.TestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static addressbook.tests.consts.TestConsts.*;

public class GroupModifyTest extends TestBase {

    @BeforeMethod
    public void gotoGroupPageAndCheckInit() {
        app.getNavigationHelper().gotoGroupPage();

        if (! app.getGroupHelper().isGroupsFound()) {
            app.getGroupHelper().createGroup(new GroupData("testgroup", null, null));
        }
    }

    @Test
    public void testGroupModifyNoEdit() {
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().initGroupModify();
        app.getGroupHelper().submitGroupModify();
        app.getGroupHelper().returnToGroupPage();
    }

    @Test
    public void testGroupModifyFillForms() {
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().initGroupModify();
        app.getGroupHelper().fillGroupForms(new GroupData("testgroup", "head", "foot"));
        app.getGroupHelper().submitGroupModify();
        app.getGroupHelper().returnToGroupPage();
    }

    @Test
    public void testGroupModifyEditForms() {
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().initGroupModify();
        app.getGroupHelper().addTextToGroupForms(TEXT_ADDED_TO_FORM);
        app.getGroupHelper().submitGroupModify();
        app.getGroupHelper().returnToGroupPage();
    }

    @Test
    public void testGroupModifyClearForms() {
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().initGroupModify();
        app.getGroupHelper().clearGroupForms();
        app.getGroupHelper().submitGroupModify();
        app.getGroupHelper().returnToGroupPage();
    }
}
