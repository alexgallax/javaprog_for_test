package addressbook.tests.grouptests;

import addressbook.model.GroupData;
import addressbook.tests.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static addressbook.tests.consts.TestConsts.*;

public class GroupModifyTest extends TestBase {

    private List<GroupData> before;
    private int groupIndex;
    private GroupData group;

    @BeforeMethod
    public void gotoGroupPageAndCheckInit() {
        app.getNavigationHelper().gotoGroupPage();

        if (! app.getGroupHelper().isGroupsFound()) {
            app.getGroupHelper().createGroup(new GroupData("testgroup", null, null));
        }

        before = app.getGroupHelper().getGroupList();
    }

    @Test
    public void testGroupModifyNoEdit() {
        groupIndex = 0;
        group = before.get(groupIndex);

        app.getGroupHelper().selectGroup(groupIndex);
        app.getGroupHelper().initGroupModify();
        app.getGroupHelper().submitGroupModify();
        app.getGroupHelper().returnToGroupPage();
    }

    @Test
    public void testGroupModifyFillForms() {
        groupIndex = before.size() - 1;
        group = new GroupData(before.get(groupIndex).getId(), "testgroup", "head", "foot");

        app.getGroupHelper().selectGroup(groupIndex);
        app.getGroupHelper().initGroupModify();
        app.getGroupHelper().fillGroupForms(group);
        app.getGroupHelper().submitGroupModify();
        app.getGroupHelper().returnToGroupPage();
    }

    @Test
    public void testGroupModifyEditForms() {
        groupIndex = before.size() - 1;
        group = new GroupData(before.get(groupIndex).getId(),
                (before.get(groupIndex).getGroupName() + TEXT_ADDED_TO_FORM),
                (before.get(groupIndex).getGroupHeader() + TEXT_ADDED_TO_FORM),
                (before.get(groupIndex).getGroupFooter() + TEXT_ADDED_TO_FORM));

        app.getGroupHelper().selectGroup(groupIndex);
        app.getGroupHelper().initGroupModify();
        app.getGroupHelper().addTextToGroupForms(TEXT_ADDED_TO_FORM);
        app.getGroupHelper().submitGroupModify();
        app.getGroupHelper().returnToGroupPage();
    }

    @Test
    public void testGroupModifyClearForms() {
        groupIndex = 0;
        group = new GroupData(before.get(groupIndex).getId(), "", "", "");

        app.getGroupHelper().selectGroup(groupIndex);
        app.getGroupHelper().initGroupModify();
        app.getGroupHelper().clearGroupForms();
        app.getGroupHelper().submitGroupModify();
        app.getGroupHelper().returnToGroupPage();
    }

    @AfterMethod
    public void makeChecks() {
        List<GroupData> after = app.getGroupHelper().getGroupList();

        Assert.assertEquals(after.size(), before.size());

        before.remove(groupIndex);
        before.add(group);

        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(after, before);
    }
}
