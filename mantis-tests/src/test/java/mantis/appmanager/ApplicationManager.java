package mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static mantis.appmanager.consts.AppConsts.*;
import static mantis.tests.consts.TestConsts.PROP_FILEPATH;
import static org.openqa.selenium.remote.BrowserType.*;

public class ApplicationManager {

    private final Properties properties;
    private String browser;

    private FtpHelper ftpHelper;
    private UserHelper userHelper;
    private AdminHelper adminHelper;
    private MailHelper mailHelper;
    private DbHelper dbHelper;

    private WebDriver wd;

    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty(TARGET_PROPS, DEFAULT_PROPS);
        properties.load(new FileReader(new File(String.format(PROP_FILEPATH, target))));
    }

    public void stop() {
        if (wd != null) {
            wd.quit();
        }
    }

    public String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }

    public WebDriver getDriver() {
        if (wd == null) {
            if (browser.equals(FIREFOX)) {
                wd = new FirefoxDriver();
            } else if (browser.equals(CHROME)) {
                wd = new ChromeDriver();
            } else if (browser.equals(IE)) {
                wd = new InternetExplorerDriver();
            }
            wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            wd.get(properties.getProperty(WEB_BASE_URL_PROP));
        }

        return wd;
    }

    public HttpSession newSession() {
        return (new HttpSession(this));
    }

    public FtpHelper ftp() {
        if (ftpHelper == null) {
            ftpHelper = new FtpHelper(this);
        }
        return ftpHelper;
    }

    public UserHelper user() {
        if (userHelper == null) {
            userHelper = new UserHelper(this);
        }
        return userHelper;
    }

    public AdminHelper admin() {
        if (adminHelper == null) {
            adminHelper = new AdminHelper(this);
        }
        return adminHelper;
    }

    public MailHelper mail() {
        if (mailHelper == null) {
            mailHelper = new MailHelper(this);
        }
        return mailHelper;
    }

    public DbHelper db() {
        if (dbHelper == null) {
            dbHelper = new DbHelper();
        }
        return dbHelper;
    }
}
