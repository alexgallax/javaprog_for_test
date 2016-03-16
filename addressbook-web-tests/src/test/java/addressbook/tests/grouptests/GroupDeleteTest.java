package addressbook.tests.grouptests;

import addressbook.model.GroupData;
import addressbook.tests.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class GroupDeleteTest extends TestBase {

    private List<GroupData> before;
    private int groupIndex;

    @BeforeMethod
    public void gotoGroupPageAndCheckInit() {
        app.getNavigationHelper().gotoGroupPage();

        if (! app.getGroupHelper().isGroupsFound()) {
            app.getGroupHelper().createGroup(new GroupData("testgroup", null, null));
        }

        before = app.getGroupHelper().getGroupList();
    }

    @Test
    public void testGroupDelete() {
        groupIndex = before.size() - 1;

        app.getGroupHelper().selectGroup(groupIndex);
        app.getGroupHelper().initGroupDelete();
        app.getGroupHelper().returnToGroupPage();

        makeChecks();
    }

    private void makeChecks() {
        List<GroupData> after = app.getGroupHelper().getGroupList();

        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(groupIndex);

        Assert.assertEquals(after, before);
    }
}
