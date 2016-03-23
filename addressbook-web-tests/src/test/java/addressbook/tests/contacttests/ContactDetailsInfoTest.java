package addressbook.tests.contacttests;

import addressbook.model.ContactData;
import addressbook.tests.TestBase;
import addressbook.tests.utils.TestFuncs;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.stream.Collectors;

import static addressbook.tests.utils.TestFuncs.cleanSpaces;
import static addressbook.tests.utils.TestFuncs.mergeSpaces;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDetailsInfoTest extends TestBase {

    @BeforeMethod
    public void gotoContactPageAndCheckInit() {
        app.goTo().gotoHomePage();

        if (! app.contact().isContactsFound()) {
            app.contact().create(new ContactData()
                    .withFirstName("New")
                    .withMiddleName("A")
                    .withLastName("Contact")
                    .withNickname(" Mike ")
                    .withAddress("unlocated house")
                    .withMobilePhone("111-11-11")
                    .withWorkPhone("0000 91")
                    .withEmail("   new.co    ntacta.@testmail.ru")
                    .withEmail2("ul@z.com    ")
                    .withHomepage("hazzo.org")
                    .withNotes("some notes           maybe            "));
            app.goTo().gotoHomePage();
        }
    }

    @Test
    public void testContactDetailsInfo() {
        ContactData contactMainPage = app.contact().all().iterator().next();
        ContactData contactEditPage = app.contact().initEditFormInfo(contactMainPage);

        app.goTo().gotoHomePage();
        ContactData contactDetailsPage = app.contact().initDetailsFormInfo(contactMainPage);

        assertThat(contactDetailsPage.getDetails(), equalTo(makeDetails(contactEditPage)));
    }

    private String makeDetails(ContactData contact) {
        String allText = "";

        String namesBlock = makeNamesBlock(contact);
        String phonesBlock = makePhonesBlock(contact);
        String emailsBlock = makeEmailsBlock(contact);
        String datesBlock = makeDatesBlock(contact);
        String address2Block = makeAddress2Block(contact);
        String homePhone2Block = makeHomephone2Block(contact);
        String notesBlock = makeNotesBlock(contact);

        if (! namesBlock.equals("")) {
            allText += namesBlock;
        }

        if (! phonesBlock.equals("")) {
            allText += "\n\n" + phonesBlock;
        }

        if (! emailsBlock.equals("")) {
            allText += "\n\n" + emailsBlock;
        }

        if (! datesBlock.equals("")) {
            allText += "\n\n" + datesBlock;
        }

        if (datesBlock.equals("")) {
            if (! address2Block.equals("") || ! homePhone2Block.equals("") || ! notesBlock.equals("")) {
                allText += "\n";
            }
        } else if (address2Block.equals("") && ! homePhone2Block.equals("")) {
            allText += "\n";
        }

        if (! address2Block.equals("")) {
            allText += "\n\n" + address2Block;
        }

        if (! homePhone2Block.equals("")) {
            allText += "\n\n" + homePhone2Block;
        }

        if (! notesBlock.equals("")) {
            allText += "\n\n" + notesBlock;
        }

        return allText;
    }

    private String makeNamesBlock(ContactData contact) {
        String fullName = Arrays.asList(
                contact.getFirstName(), contact.getMiddleName(), contact.getLastName())
                .stream()
                .filter((s) -> !s.equals(""))
                .map(TestFuncs::cleanSpaces)
                .collect(Collectors.joining(" "));

        return Arrays.asList(
                fullName, contact.getNickname(), contact.getTitle(), contact.getCompany(), contact.getAddress())
                .stream()
                .filter((s) -> !s.equals(""))
                .map(TestFuncs::cleanSpaces)
                .collect(Collectors.joining("\n"));
    }

    private String makePhonesBlock(ContactData contact) {
        return Arrays.asList(
                "H: " + contact.getHomePhone(),
                "M: " + contact.getMobilePhone(),
                "W: " + contact.getWorkPhone(),
                "F: " + contact.getFax())
                .stream()
                .filter((s) -> (!s.equals("H: ") && !s.equals("M: ") && !s.equals("W: ") && !s.equals("F: ")))
                .map(TestFuncs::cleanSpaces)
                .collect(Collectors.joining("\n"));
    }

    private String makeEmailsBlock(ContactData contact) {
        String allEmails = Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream()
                .filter((s) -> !s.equals(""))
                .map(ContactDetailsInfoTest::addUrlToEmailCleaned)
                .collect(Collectors.joining("\n"));

        if (! contact.getHomepage().equals("")) {
            allEmails += "\n" + "Homepage:\n" + cleanSpaces(contact.getHomepage());
        }

        return allEmails;
    }

    private String makeDatesBlock(ContactData contact) {
        return Arrays.asList(
                "Birthday " + makeDate(contact.getBday(), contact.getBmonth(), contact.getByear(), true),
                "Anniversary " + makeDate(contact.getAday(), contact.getAmonth(), contact.getAyear(), false))
                .stream()
                .filter((s) -> (!s.equals("Birthday ") && !s.equals("Anniversary ")))
                .collect(Collectors.joining("\n"));
    }

    private String makeAddress2Block(ContactData contact) {
        return cleanSpaces(contact.getAddress2());
    }

    private String makeHomephone2Block(ContactData contact) {
        String homePhone2 = "";
        if (! contact.getHomePhone2().equals("")) {
            homePhone2 = "P: " + cleanSpaces(contact.getHomePhone2());
        }

        return homePhone2;
    }

    private String makeNotesBlock(ContactData contact) {
        return cleanSpaces(contact.getNotes());
    }

    private String makeDate(String day, String month, String year, boolean countYears) {
        String dayText = "";
        if (! day.equals("0")) {
            dayText = day + ". ";
        }

        String monthText = "";
        if (! month.equals("-")) {
            monthText = month.substring(0, 1).toUpperCase() + month.substring(1, month.length()).toLowerCase() + " ";
        }

        String passedYearsText = "";
        if (! year.equals("")) {
            int contactYear = Integer.parseInt(year);

            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);

            int passedYears = currentYear - contactYear;

            if (passedYears >= 0) {
                passedYearsText = " (" + String.valueOf(passedYears) + ")";
            }
        }
        return dayText + monthText + year + passedYearsText;
    }

    private static String addUrlToEmailCleaned(String email) {
        return cleanSpaces(email) + " (www." + mergeSpaces(email.split("@")[1]) + ")";
    }
}
