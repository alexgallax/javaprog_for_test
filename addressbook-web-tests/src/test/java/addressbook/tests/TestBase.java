package addressbook.tests;

import addressbook.appmanager.ApplicationManager;
import addressbook.model.ContactData;
import addressbook.model.GroupData;
import addressbook.model.Items;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.stream.Collectors;

import static addressbook.tests.consts.TestConsts.DEFAULT_TEST_BROWSER;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBase {

    protected static final ApplicationManager app
            = new ApplicationManager(System.getProperty("browser", DEFAULT_TEST_BROWSER));

    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite
    public void tearDown() {
        app.stop();
    }

    protected String readTestDataToJson(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
        String json = "";
        String line = reader.readLine();
        while (line != null) {
            json += line;
            line = reader.readLine();
        }
        reader.close();

        return json;
    }

    protected void verifyGroupsInUI() {
        if (Boolean.getBoolean("verifyUI")) {
            Items<GroupData> dbGroups = app.db().groups();
            Items<GroupData> uiGroups = app.group().all();

            assertThat(uiGroups, equalTo(
                    dbGroups
                            .stream()
                            .map((g) -> new GroupData()
                                    .withId(g.getId())
                                    .withName(g.getGroupName()))
                            .collect(Collectors.toSet())));
        }
    }

    protected void verifyContactsInUI() {
        if (Boolean.getBoolean("verifyUI")) {
            Items<ContactData> dbContacts = app.db().contacts();
            Items<ContactData> uiContacts = app.contact().all();

            assertThat(uiContacts
                    .stream()
                    .map((c) -> new ContactData()
                            .withId(c.getId())
                            .withFirstName(c.getFirstName())
                            .withLastName(c.getLastName()))
                    .collect(Collectors.toSet()),
                    equalTo(
                            dbContacts
                                    .stream()
                                    .map((c) -> new ContactData()
                                            .withId(c.getId())
                                            .withFirstName(c.getFirstName())
                                            .withLastName(c.getLastName()))
                                    .collect(Collectors.toSet())));
        }
    }

    protected String generateRandomName(int length) {
        String name = "";
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomInt = random.nextInt(10);
            name += String.valueOf(randomInt);
        }

        return name;
    }
}
