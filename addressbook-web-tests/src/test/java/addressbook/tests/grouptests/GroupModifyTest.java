package addressbook.tests.grouptests;

import addressbook.model.GroupData;
import addressbook.tests.TestBase;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

import static addressbook.tests.consts.TestConsts.*;

public class GroupModifyTest extends TestBase {

    private List<GroupData> before;
    private int groupIndex;
    private GroupData group;

    @BeforeMethod
    public void gotoGroupPageAndCheckInit() {
        app.goTo().groupPage();

        if (! app.group().isGroupsFound()) {
            app.group().create(new GroupData("testgroup", null, null));
        }

        before = app.group().list();
    }

    @Test
    public void testGroupModifyNoEdit() {
        groupIndex = 0;
        group = before.get(groupIndex);

        app.group().noEdit(groupIndex);

        makeChecks();
    }

    @Test
    public void testGroupModifyFillForms() {
        groupIndex = before.size() - 1;
        group = new GroupData(before.get(groupIndex).getId(), "testgroup", "head", "foot");

        app.group().modify(groupIndex, group);

        makeChecks();
    }

    @Test
    public void testGroupModifyEditForms() {
        groupIndex = before.size() - 1;
        group = new GroupData(before.get(groupIndex).getId(),
                (before.get(groupIndex).getGroupName() + TEXT_ADDED_TO_FORM),
                null,
                null);

        app.group().edit(groupIndex, TEXT_ADDED_TO_FORM);

        makeChecks();
    }

    @Test
    public void testGroupModifyClearForms() {
        groupIndex = 0;
        group = new GroupData(before.get(groupIndex).getId(), "", "", "");

        app.group().clear(groupIndex);

        makeChecks();
    }

    private void makeChecks() {
        List<GroupData> after = app.group().list();

        Assert.assertEquals(after.size(), before.size());

        before.remove(groupIndex);
        before.add(group);

        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(after, before);
    }
}
