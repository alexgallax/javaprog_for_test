package addressbook.tests.grouptests;

import addressbook.model.GroupData;
import addressbook.model.Items;
import addressbook.tests.TestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreateTest extends TestBase {

    private Items<GroupData> before;
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
        Items<GroupData> after = app.group().all();

        assertThat(after.size(), equalTo(before.size() + 1));
        assertThat(after, equalTo(
                before
                        .withAdded(group
                                .withId(after.stream().mapToInt(g -> g.getId()).max().getAsInt()))));
    }
}
