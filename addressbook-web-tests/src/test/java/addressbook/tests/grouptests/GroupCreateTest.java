package addressbook.tests.grouptests;

import addressbook.model.GroupData;
import addressbook.tests.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

public class GroupCreateTest extends TestBase {

    private List<GroupData> before;
    private GroupData group;

    @BeforeMethod
    public void gotoGroupPageAndCheckInit() {
        app.getNavigationHelper().gotoGroupPage();

        before = app.getGroupHelper().getGroupList();
    }

    @Test
    public void testGroupCreate() {
        group = new GroupData("testgroup", null, null);

        app.getGroupHelper().createGroup(group);

        makeChecks();
    }

    private void makeChecks() {
        List<GroupData> after = app.getGroupHelper().getGroupList();

        Assert.assertEquals(after.size(), before.size() + 1);

        before.add(group);

        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(after, before);
    }
}
