package mantis.appmanager;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static mantis.appmanager.AppConsts.*;

public class FtpHelper {

    private final ApplicationManager app;
    private FTPClient ftp;

    public FtpHelper(ApplicationManager app) {
        this.app = app;

    }

    public void upload(File file, String target, String backup) throws IOException {
        ftp.connect(app.getProperty(FTP_HOST_PROP));
        ftp.login(app.getProperty(FTP_LOGIN_PROP), app.getProperty(FTP_PASSWORD_PROP));
        ftp.deleteFile(backup);
        ftp.rename(target, backup);
        ftp.enterLocalPassiveMode();
        ftp.storeFile(target, new FileInputStream(file));
        ftp.disconnect();
    }

    public void restore(String backup, String target) throws IOException {
        ftp.connect(app.getProperty(FTP_HOST_PROP));
        ftp.login(app.getProperty(FTP_LOGIN_PROP), app.getProperty(FTP_PASSWORD_PROP));
        ftp.deleteFile(target);
        ftp.rename(backup, target);
        ftp.disconnect();
    }
}
