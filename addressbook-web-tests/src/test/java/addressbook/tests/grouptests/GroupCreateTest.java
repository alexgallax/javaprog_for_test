package addressbook.tests.grouptests;

import addressbook.model.GroupData;
import addressbook.tests.TestBase;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class GroupCreateTest extends TestBase {

    private Set<GroupData> before;
    private GroupData group;

    @BeforeMethod
    public void gotoGroupPageAndCheckInit() {
        app.goTo().groupPage();

        before = app.group().all();
    }

    @Test
    public void testGroupCreate() {
        group = new GroupData()
                .withName("testgroup");

        app.group().create(group);

        makeChecks();
    }

    private void makeChecks() {
        Set<GroupData> after = app.group().all();

        Assert.assertEquals(after.size(), before.size() + 1);

        group.withId(after.stream().mapToInt(g -> g.getId()).max().getAsInt());
        before.add(group);

        Assert.assertEquals(after, before);
    }
}
