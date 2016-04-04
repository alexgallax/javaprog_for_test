package addressbook.tests.contacttests;

import addressbook.model.ContactData;
import addressbook.model.GroupData;
import addressbook.model.Items;
import addressbook.tests.TestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactGroupTest extends TestBase {

    private ContactData contact;
    private GroupData group;
    private String groupName = "";
    private int groupNameLength = 10;

    @BeforeMethod
    public void gotoContactPageAndCheckInit() {

        boolean noSuchGroupName = false;
        while (!noSuchGroupName) {
            noSuchGroupName = true;
            groupName = generateRandomName(groupNameLength);
            Items<GroupData> allGroups = app.db().groups();

            for (GroupData group : allGroups) {
                if (group.getGroupName().equals(groupName)) {
                    noSuchGroupName = false;
                    break;
                }
            }
        }
        group = new GroupData().withName(groupName);

        app.goTo().groupPage();
        app.group().create(group);

        app.goTo().gotoHomePage();
        if (! app.contact().isContactsFound()) {
            app.contact().create(new ContactData()
                    .withFirstName("New")
                    .withMiddleName("A")
                    .withLastName("Contact")
                    .withAddress("unlocated house")
                    .withMobilePhone("111-11-11")
                    .withEmail("new.contacta.@testmail.ru"));
            app.goTo().gotoHomePage();
        }

        contact = app.db().contacts().iterator().next();

    }

    @Test
    public void testContactAddToGroup() {
        Items<GroupData> beforeContactGroups = contact.getGroups();
        app.contact().addToGroup(contact, group);

        Items<GroupData> afterContactGroups = app.db().getContact(contact.getId()).getGroups();
        assertThat(afterContactGroups.size(), equalTo(beforeContactGroups.size() + 1));

        assertThat(afterContactGroups
                        .stream()
                        .map((g) -> new GroupData()
                                .withName(g.getGroupName()))
                        .collect(Collectors.toSet()),
                equalTo(beforeContactGroups
                        .withAdded(new GroupData()
                                .withName(group.getGroupName()))
                        .stream()
                        .map((g) -> new GroupData()
                                .withName(g.getGroupName()))
                        .collect(Collectors.toSet())));
    }

    @Test
    public void testContactRemoveFromGroup() {
        app.contact().addToGroup(contact, group);
        app.goTo().gotoHomePage();
        Items<GroupData> beforeContactGroups = app.db().getContact(contact.getId()).getGroups();

        app.contact().removeFromGroup(group);

        Items<GroupData> afterContactGroups = app.db().getContact(contact.getId()).getGroups();
        assertThat(afterContactGroups.size(), equalTo(beforeContactGroups.size() - 1));

        assertThat(afterContactGroups
                .withAdded(new GroupData()
                        .withName(group.getGroupName()))
                        .stream()
                        .map((g) -> new GroupData()
                                .withName(g.getGroupName()))
                        .collect(Collectors.toSet()),
                equalTo(beforeContactGroups
                        .stream()
                        .map((g) -> new GroupData()
                                .withName(g.getGroupName()))
                        .collect(Collectors.toSet())));
    }
}
