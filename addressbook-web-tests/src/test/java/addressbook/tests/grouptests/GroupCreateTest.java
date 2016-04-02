package addressbook.tests.grouptests;

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

import static addressbook.tests.consts.TestConsts.TEST_GROUPS_JSON_FILEPATH;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreateTest extends TestBase {

    private Items<GroupData> before;

    @DataProvider
    public Iterator<Object[]> testGroups() throws IOException {
        String json = readTestDataToJson(TEST_GROUPS_JSON_FILEPATH);

        Gson gson = new Gson();
        List<GroupData> groups = gson.fromJson(json, new TypeToken<List<GroupData>>(){}.getType());

        return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }

    @BeforeMethod
    public void gotoGroupPageAndCheckInit() {
        app.goTo().groupPage();

        before = app.db().groups();
    }

    @Test(dataProvider = "testGroups")
    public void testGroupCreate(GroupData group) {
        app.group().create(group);

        makeChecks(group);
    }

    private void makeChecks(GroupData group) {
        assertThat(app.group().count(), equalTo(before.size() + 1));

        Items<GroupData> after = app.db().groups();
        assertThat(after, equalTo(
                before
                        .withAdded(group
                                .withId(after.stream().mapToInt(g -> g.getId()).max().getAsInt()))));
    }
}
