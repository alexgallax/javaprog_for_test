package addressbook.tests.grouptests;

import addressbook.model.GroupData;
import addressbook.model.Items;
import addressbook.tests.TestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeleteTest extends TestBase {

    private Items<GroupData> before;
    private GroupData group;

    @BeforeMethod
    public void gotoGroupPageAndCheckInit() {
        app.goTo().groupPage();

        if (! app.group().isGroupsFound()) {
            app.group().create(new GroupData()
                    .withName("testgroup"));
        }

        before = app.db().groups();
        group = before.iterator().next();
    }

    @Test
    public void testGroupDelete() {
        app.group().delete(group);

        makeChecks();
    }

    private void makeChecks() {
        assertThat(app.group().count(), equalTo(before.size() - 1));

        Items<GroupData> after = app.db().groups();
        assertThat(after, equalTo(
                before
                        .without(group)));
    }
}
