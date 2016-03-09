package addressbook.tests.grouptests;

import addressbook.model.GroupData;
import addressbook.tests.TestBase;
import org.testng.annotations.Test;

public class GroupCreateTest extends TestBase {

    @Test
    public void testGroupCreate() {
        app.getNavigationHelper().gotoGroupPage();
        app.getGroupHelper().createGroup(new GroupData("testgroup", null, null));
    }
}
