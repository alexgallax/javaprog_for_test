package addressbook.tests;

import addressbook.model.GroupData;
import org.testng.annotations.Test;

public class GroupCreateTest extends TestBase {

    @Test
    public void testGroupCreate() {
        app.gotoGroupPage();
        app.initGroupCreate();
        app.fillGroupForms(new GroupData("testgroup", "head", "foot"));
        app.submitGroupCreate();
        app.returnToGroupPage();
    }
}
