package addressbook.tests.grouptests;

import addressbook.tests.TestBase;
import org.testng.annotations.Test;

public class GroupDeleteTest extends TestBase {

    @Test
    public void testGroupDelete() {
        app.getNavigationHelper().gotoGroupPage();
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().initGroupDelete();
        app.getGroupHelper().returnToGroupPage();
    }
}
