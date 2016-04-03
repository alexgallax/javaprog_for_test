package addressbook.tests.grouptests;

import addressbook.model.GroupData;
import addressbook.model.Items;
import addressbook.tests.TestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static addressbook.tests.consts.TestConsts.TEXT_ADDED_TO_FORM;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModifyTest extends TestBase {

    private Items<GroupData> before;
    private GroupData modifiedGroup;
    private GroupData group;

    @BeforeMethod
    public void gotoGroupPageAndCheckInit() {
        app.goTo().groupPage();

        if (! app.group().isGroupsFound()) {
            app.group().create(new GroupData()
                    .withName("testgroup"));
        }

        before = app.db().groups();
        modifiedGroup = before.iterator().next();
    }

    @Test
    public void testGroupModifyNoEdit() {
        group = modifiedGroup;

        app.group().noEdit(group);

        makeChecks();
    }

    @Test
    public void testGroupModifyFillForms() {
        group = new GroupData()
                .withId(modifiedGroup.getId())
                .withName("testgroup")
                .withHeader("head")
                .withFooter("foot");

        app.group().modify(group);

        makeChecks();
    }

    @Test
    public void testGroupModifyEditForms() {
        group = new GroupData()
                .withId(modifiedGroup.getId())
                .withName(modifiedGroup.getGroupName() + TEXT_ADDED_TO_FORM)
                .withHeader(modifiedGroup.getGroupHeader() + TEXT_ADDED_TO_FORM)
                .withFooter(modifiedGroup.getGroupFooter() + TEXT_ADDED_TO_FORM);

        app.group().edit(group, TEXT_ADDED_TO_FORM);

        makeChecks();
    }

    @Test
    public void testGroupModifyClearForms() {
        group = new GroupData()
                .withId(modifiedGroup.getId())
                .withName("")
                .withHeader("")
                .withFooter("");

        app.group().clear(group);

        makeChecks();
    }

    private void makeChecks() {
        assertThat(app.group().count(), equalTo(before.size()));

        Items<GroupData> after = app.db().groups();
        assertThat(after, equalTo(
                before
                        .without(modifiedGroup)
                        .withAdded(group)));

        verifyGroupsInUI();
    }
}
