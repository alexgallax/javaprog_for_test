package addressbook.tests.grouptests;

import addressbook.tests.TestBase;
import org.testng.annotations.Test;

public class GroupModifyTest extends TestBase {

    @Test
    public void testGroupModify() {
        app.getNavigationHelper().gotoGroupPage();
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().initGroupModify();
        app.getGroupHelper().submitGroupModify();
        app.getGroupHelper().returnToGroupPage();
    }
}
