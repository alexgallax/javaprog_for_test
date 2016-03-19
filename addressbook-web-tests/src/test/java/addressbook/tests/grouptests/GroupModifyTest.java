package addressbook.tests.grouptests;

import addressbook.model.GroupData;
import addressbook.tests.TestBase;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static addressbook.tests.consts.TestConsts.*;

public class GroupModifyTest extends TestBase {

    private Set<GroupData> before;
    private GroupData modifiedGroup;
    private GroupData group;

    @BeforeMethod
    public void gotoGroupPageAndCheckInit() {
        app.goTo().groupPage();

        if (! app.group().isGroupsFound()) {
            app.group().create(new GroupData()
                    .withName("testgroup"));
        }

        before = app.group().all();
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
                .withName(modifiedGroup.getGroupName() + TEXT_ADDED_TO_FORM);

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
        Set<GroupData> after = app.group().all();

        Assert.assertEquals(after.size(), before.size());

        before.remove(modifiedGroup);
        before.add(group);

        Assert.assertEquals(after, before);
    }
}
