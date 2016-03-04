package addressbook.tests;

import addressbook.model.GroupData;
import org.testng.annotations.Test;

public class GroupCreateTest extends TestBase {

    @Test
    public void testGroupCreate() {
        app.getNavigationHelper().gotoGroupPage();
        app.getGroupHelper().initGroupCreate();
        app.getGroupHelper().fillGroupForms(new GroupData("testgroup", "head", "foot"));
        app.getGroupHelper().submitGroupCreate();
        app.getGroupHelper().returnToGroupPage();
    }
}
