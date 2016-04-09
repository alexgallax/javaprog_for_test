package addressbook.tests.contacttests;

import addressbook.model.ContactData;
import addressbook.model.GroupData;
import addressbook.model.Items;
import addressbook.tests.TestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactGroupTest extends TestBase {

    private ContactData contact;
    private GroupData group;
    private String groupName = "";
    private int groupNameLength = 10;

    @BeforeMethod
    public void gotoContactPageAndCheckInit() {

        Items<GroupData> beforeAllGroups = app.db().groups();

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
        app.goTo().groupPage();
        app.group().create(new GroupData().withName(groupName));

        Items<GroupData> afterAllGroups = app.db().groups();
        afterAllGroups.removeAll(beforeAllGroups);
        group = afterAllGroups.iterator().next();

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
        Items<ContactData> beforeGroupContacts = group.getContacts();
        app.contact().addToGroup(contact, group);

        Items<GroupData> afterContactGroups = app.db().getContact(contact.getId()).getGroups();
        assertThat(afterContactGroups.size(), equalTo(beforeContactGroups.size() + 1));

        Items<ContactData> afterGroupContacts = app.db().getGroup(group.getId()).getContacts();
        assertThat(afterGroupContacts.size(), equalTo(beforeGroupContacts.size() + 1));

        assertThat(afterContactGroups,
                equalTo(beforeContactGroups
                        .withAdded(group)));
    }

    @Test
    public void testContactRemoveFromGroup() {
        app.contact().addToGroup(contact, group);
        app.goTo().gotoHomePage();
        Items<GroupData> beforeContactGroups = app.db().getContact(contact.getId()).getGroups();
        Items<ContactData> beforeGroupContacts = app.db().getGroup(group.getId()).getContacts();

        app.contact().removeFromGroup(group);

        Items<GroupData> afterContactGroups = app.db().getContact(contact.getId()).getGroups();
        assertThat(afterContactGroups.size(), equalTo(beforeContactGroups.size() - 1));

        Items<ContactData> afterGroupContacts = app.db().getGroup(group.getId()).getContacts();
        assertThat(afterGroupContacts.size(), equalTo(beforeGroupContacts.size() - 1));

        assertThat(afterContactGroups,
                equalTo(beforeContactGroups
                        .without(group)));
    }
}
