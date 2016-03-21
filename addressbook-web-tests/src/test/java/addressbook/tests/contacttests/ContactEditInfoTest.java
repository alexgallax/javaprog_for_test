package addressbook.tests.contacttests;

import addressbook.model.ContactData;
import addressbook.tests.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEditInfoTest extends TestBase {

    private ContactData contactMainPage;
    private ContactData contactEditForm;

    @BeforeClass
    public void gotoContactPageAndCheckInit() {
        app.goTo().gotoHomePage();

        if (! app.contact().isContactsFound()) {
            app.contact().create(new ContactData()
                    .withFirstName("New")
                    .withMiddleName("A")
                    .withLastName("Contact")
                    .withAddress("unlocated house")
                    .withHomePhone("+7 (222) 111-11-11")
                    .withMobilePhone("111-11-11")
                    .withEmail("new.contacta.@testmail.ru")
                    .withEmail2("  new_c@tst.com  "));
            app.goTo().gotoHomePage();
        }

        contactMainPage = app.contact().all().iterator().next();
        contactEditForm = app.contact().initEditFormInfo(contactMainPage);
    }

    @Test
    public void testContactAddress() {
        assertThat(contactMainPage.getAddress(), equalTo(clean(contactEditForm.getAddress())));
    }

    @Test
    public void testContactEmails() {
        assertThat(contactMainPage.getAllEmails(), equalTo(mergeEmails(contactEditForm)));
    }

    @Test
    public void testContactPhones() {
        assertThat(contactMainPage.getAllPhones(), equalTo(mergePhones(contactEditForm)));
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(
                contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream()
                .filter((s) -> ! s.equals(""))
                .map(ContactEditInfoTest::clean)
                .collect(Collectors.joining("\n"));
    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(
                contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone(), contact.getHomePhone2())
                .stream()
                .filter((s) -> ! s.equals(""))
                .map(ContactEditInfoTest::cleanPhones)
                .collect(Collectors.joining("\n"));
    }

    private static String clean(String str) {
        return str.replaceAll("^\\s*", "").replaceAll("\\s*$", "");
    }

    private static String cleanPhones(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }
}
