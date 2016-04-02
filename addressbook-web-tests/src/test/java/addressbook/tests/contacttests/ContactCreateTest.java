package addressbook.tests.contacttests;

import addressbook.model.ContactData;
import addressbook.model.GroupData;
import addressbook.model.Items;
import addressbook.tests.TestBase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static addressbook.tests.consts.TestConsts.TEST_CONTACTS_JSON_FILEPATH;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreateTest extends TestBase {

    public static final String GROUP_NAME = "testgroup";

    private Items<ContactData> before;

    @DataProvider
    public Iterator<Object[]> testContacts() throws IOException {
        String json = readTestDataToJson(TEST_CONTACTS_JSON_FILEPATH);

        Gson gson = new Gson();
        List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>(){}.getType());

        return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();
    }

    @BeforeMethod
    public void checkGroupForContactPresent() {
        app.goTo().gotoHomePage();
        app.contact().initContactCreate();

        if (! app.contact().checkGroupForContact(GROUP_NAME)) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName(GROUP_NAME));
        }

        app.goTo().gotoHomePage();

        before = app.db().contacts();
    }

    @Test(dataProvider = "testContacts")
    public void testContactCreate(ContactData contact) {
        app.contact().create(contact);
        app.goTo().gotoHomePage();

        makeChecks(contact);
    }

    private void makeChecks(ContactData contact) {
        assertThat(app.contact().count(), equalTo(before.size() + 1));

        Items<ContactData> after = app.db().contacts();
        assertThat(after, equalTo(
                before
                        .withAdded(contact
                                .withId(after.stream().mapToInt(c -> c.getId()).max().getAsInt()))));
    }
}
