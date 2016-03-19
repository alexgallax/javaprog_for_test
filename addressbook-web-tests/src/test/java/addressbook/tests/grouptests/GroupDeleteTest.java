package addressbook.tests.grouptests;

import addressbook.model.GroupData;
import addressbook.tests.TestBase;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class GroupDeleteTest extends TestBase {

    private List<GroupData> before;
    private int groupIndex;

    @BeforeMethod
    public void gotoGroupPageAndCheckInit() {
        app.goTo().groupPage();

        if (! app.group().isGroupsFound()) {
            app.group().create(new GroupData()
                    .withName("testgroup"));
        }

        before = app.group().list();
    }

    @Test
    public void testGroupDelete() {
        groupIndex = before.size() - 1;

        app.group().delete(groupIndex);

        makeChecks();
    }

    private void makeChecks() {
        List<GroupData> after = app.group().list();

        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(groupIndex);

        Assert.assertEquals(after, before);
    }
}
