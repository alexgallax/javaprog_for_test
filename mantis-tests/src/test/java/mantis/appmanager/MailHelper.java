package mantis.appmanager;

import mantis.model.MailMessage;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MailHelper {

    private static final int WAIT_MAIL = 1000;

    private ApplicationManager app;
    private final Wiser wiser;

    public MailHelper(ApplicationManager app) {
        this.app = app;
        wiser = new Wiser();
    }

    public void start() {
        wiser.start();
    }

    public void stop() {
        wiser.stop();
    }

    public List<MailMessage> waitForMail(int timeout) {
        return waitForMail(1, timeout);
    }

    public List<MailMessage> waitForMail(int count, int timeout) {
        long start = System.currentTimeMillis();

        while (System.currentTimeMillis() < (start + timeout)) {
            if (wiser.getMessages().size() >= count) {
                return wiser.getMessages()
                        .stream()
                        .map((m) -> toModelMail(m))
                        .collect(Collectors.toList());
            }
            try {
                Thread.sleep(WAIT_MAIL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw (new Error("No mail"));
    }

    private MailMessage toModelMail(WiserMessage m) {
        try {
            MimeMessage mm = m.getMimeMessage();
            return (new MailMessage(mm.getAllRecipients()[0].toString(), (String) mm.getContent()));
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void clear() {
        wiser.getMessages().clear();
    }
}
