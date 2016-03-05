package addressbook.appmanager;

import addressbook.model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GroupHelper extends BaseHelper {

    public GroupHelper(FirefoxDriver wd) {
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

    public void returnToGroupPage() {
        click(By.linkText("group page"));
    }

    public void selectGroup() {
        if (!checkSelect(By.name("selected[]"))) {
            click(By.name("selected[]"));
        }
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
}
