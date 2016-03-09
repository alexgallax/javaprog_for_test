package addressbook.tests.grouptests;

import addressbook.model.GroupData;
import addressbook.tests.TestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GroupDeleteTest extends TestBase {

    @BeforeMethod
    public void gotoGroupPageAndCheckInit() {
        app.getNavigationHelper().gotoGroupPage();

        if (! app.getGroupHelper().isGroupsFound()) {
            app.getGroupHelper().createGroup(new GroupData("testgroup", null, null));
        }
    }

    @Test
    public void testGroupDelete() {
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().initGroupDelete();
        app.getGroupHelper().returnToGroupPage();
    }
}
