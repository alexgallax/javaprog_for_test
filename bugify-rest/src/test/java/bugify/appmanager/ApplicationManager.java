package bugify.appmanager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static bugify.appmanager.consts.AppConsts.PROP_FILEPATH;

public class ApplicationManager {

    private final Properties properties;

    private RestHelper restHelper;
    private RestAssuredHelper restAssuredHelper;

    public ApplicationManager() {
        properties = new Properties();
    }

    public void init() throws IOException {
        properties.load(new FileReader(new File(PROP_FILEPATH)));
    }

    public void stop() {

    }

    public String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }

    public RestHelper rest() {
        if (restHelper == null) {
            restHelper = new RestHelper(this);
        }
        return restHelper;
    }

    public RestAssuredHelper restAssured() {
        if (restAssuredHelper == null) {
            restAssuredHelper = new RestAssuredHelper(this);
        }
        return restAssuredHelper;
    }
}
