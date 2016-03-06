package addressbook.tests;

import addressbook.appmanager.ApplicationManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static addressbook.tests.consts.TestConsts.TEST_BROWSER;

public class TestBase {

    protected final ApplicationManager app = new ApplicationManager(TEST_BROWSER);

    @BeforeMethod
    public void setUp() throws Exception {
        app.init();
    }

    @AfterMethod
    public void tearDown() {
        app.stop();
    }
}
