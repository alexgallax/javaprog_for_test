package addressbook.generators;

import addressbook.model.ContactData;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator extends GeneratorBase {

    public static void main(String[] args) throws IOException {
        ContactDataGenerator generator = new ContactDataGenerator();
        JCommander jCommander = new JCommander(generator);
        try {
            jCommander.parse(args);
        } catch (ParameterException e) {
            jCommander.usage();
            return;
        }
        generator.run();
    }

    private void run() throws IOException {
        List<ContactData> contacts = generateContacts(count);

        if (format.equals("csv")) {
            saveCsv(contacts, new File(filePath));
        } else if (format.equals("xml")) {
            saveXml(contacts, new File(filePath));
        } else if (format.equals("json")) {
            saveJson(contacts, new File(filePath));
        }
    }

    private List<ContactData> generateContacts(int count) {
        List<ContactData> contacts = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            contacts.add(new ContactData()
                    .withFirstName("first name " + i)
                    .withLastName("last name " + i)
                    .withAddress("strait street " + i)
                    .withMobilePhone("0000" + i)
                    .withEmail("art" + i + "@dot.com"));
        }
        return contacts;
    }

    private void saveCsv(List<ContactData> contacts, File file) throws IOException {
        String csv = "";
        for (ContactData contact : contacts) {
            csv += String.format("%s;%s;%s;%s;%s\n",
                    contact.getFirstName(),
                    contact.getLastName(),
                    contact.getAddress(),
                    contact.getMobilePhone(),
                    contact.getEmail());
        }

        writeToFile(csv, file);
    }

    private void saveXml(List<ContactData> contacts, File file) throws IOException {
        XStream xStream = new XStream();
        xStream.processAnnotations(ContactData.class);
        String xml = xStream.toXML(contacts);

        writeToFile(xml, file);
    }

    private void saveJson(List<ContactData> contacts, File file) throws IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        String json = gson.toJson(contacts);

        writeToFile(json, file);
    }
}
