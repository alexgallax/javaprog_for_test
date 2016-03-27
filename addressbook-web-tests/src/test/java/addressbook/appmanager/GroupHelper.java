package addressbook.appmanager;

import addressbook.model.GroupData;
import addressbook.model.Items;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class GroupHelper extends BaseHelper {

    private Items<GroupData> groupCache = null;

    public GroupHelper(WebDriver wd) {
        super(wd);
    }

    public void submitGroupCreate() {
        click(By.name("submit"));
    }

    public void submitGroupModify() {
        click(By.name("update"));
    }

    public void fillGroupForms(GroupData groupData) {
        type(By.name("group_name"), groupData.getGroupName());
        type(By.name("group_header"), groupData.getGroupHeader());
        type(By.name("group_footer"), groupData.getGroupFooter());
    }

    public void clearGroupForms() {
        clearForm(By.name("group_name"));
        clearForm(By.name("group_header"));
        clearForm(By.name("group_footer"));
    }

    public void addTextToGroupForms(String text) {
        addTextToForm(By.name("group_name"), text);
        addTextToForm(By.name("group_header"), text);
        addTextToForm(By.name("group_footer"), text);
    }

    public void returnToGroupPage() {
        click(By.linkText("group page"));
    }

    private void selectGroupById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void initGroupCreate() {
        click(By.name("new"));
    }

    public void initGroupDelete() {
        click(By.name("delete"));
    }

    public void initGroupModify() {
        click(By.name("edit"));
    }

    public boolean isGroupsFound() {
        return isElementFound(By.name("selected[]"));
    }

    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public void create(GroupData group) {
        initGroupCreate();
        fillGroupForms(group);
        submitGroupCreate();
        groupCache = null;
        returnToGroupPage();
    }

    public void delete(GroupData group) {
        selectGroupById(group.getId());
        initGroupDelete();
        groupCache = null;
        returnToGroupPage();
    }

    public void modify(GroupData group) {
        selectGroupById(group.getId());
        initGroupModify();
        fillGroupForms(group);
        submitGroupModify();
        groupCache = null;
        returnToGroupPage();
    }

    public void noEdit(GroupData group) {
        selectGroupById(group.getId());
        initGroupModify();
        submitGroupModify();
        groupCache = null;
        returnToGroupPage();
    }

    public void edit(GroupData group, String text) {
        selectGroupById(group.getId());
        initGroupModify();
        addTextToGroupForms(text);
        submitGroupModify();
        groupCache = null;
        returnToGroupPage();
    }

    public void clear(GroupData group) {
        selectGroupById(group.getId());
        initGroupModify();
        clearGroupForms();
        submitGroupModify();
        groupCache = null;
        returnToGroupPage();
    }

    public List<GroupData> list() {
        List<GroupData> groups = new ArrayList<GroupData>();
        List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));

        for (WebElement element : elements) {
            String name = element.getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));

            groups.add(new GroupData().withId(id).withName(name));
        }

        return groups;
    }

    public Items<GroupData> all() {
        if (groupCache != null) {
            return new Items<>(groupCache);
        }

        groupCache = new Items<>();
        List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));

        for (WebElement element : elements) {
            String name = element.getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));

            groupCache.add(new GroupData()
                    .withId(id)
                    .withName(name));
        }

        return new Items<>(groupCache);
    }
}
