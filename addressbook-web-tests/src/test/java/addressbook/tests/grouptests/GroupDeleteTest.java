package addressbook.tests.grouptests;

import addressbook.model.GroupData;
import addressbook.tests.TestBase;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

public class GroupDeleteTest extends TestBase {

    private Set<GroupData> before;
    private GroupData group;

    @BeforeMethod
    public void gotoGroupPageAndCheckInit() {
        app.goTo().groupPage();

        if (! app.group().isGroupsFound()) {
            app.group().create(new GroupData()
                    .withName("testgroup"));
        }

        before = app.group().all();
    }

    @Test
    public void testGroupDelete() {
        group = before.iterator().next();

        app.group().delete(group);

        makeChecks();
    }

    private void makeChecks() {
        Set<GroupData> after = app.group().all();

        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(group);

        Assert.assertEquals(after, before);
    }
}
