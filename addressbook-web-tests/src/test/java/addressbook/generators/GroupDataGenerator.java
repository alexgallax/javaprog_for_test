package addressbook.generators;

import addressbook.model.GroupData;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator extends GeneratorBase {

    public static void main(String[] args) throws IOException {
        GroupDataGenerator generator = new GroupDataGenerator();
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
        List<GroupData> groups = generateGroups(count);

        if (format.equals("csv")) {
            saveCsv(groups, new File(filePath));
        } else if (format.equals("xml")) {
            saveXml(groups, new File(filePath));
        } else if (format.equals("json")) {
            saveJson(groups, new File(filePath));
        }
    }

    private List<GroupData> generateGroups(int count) {
        List<GroupData> groups = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            groups.add(new GroupData()
                    .withName("test" + i)
                    .withHeader("header" + i)
                    .withFooter("footer" + i));
        }
        return groups;
    }

    private void saveCsv(List<GroupData> groups, File file) throws IOException {
        String csv = "";
        for (GroupData group : groups) {
            csv += String.format("%s;%s;%s\n",
                    group.getGroupName(),
                    group.getGroupHeader(),
                    group.getGroupFooter());
        }

        writeToFile(csv, file);
    }

    private void saveXml(List<GroupData> groups, File file) throws IOException {
        XStream xStream = new XStream();
        xStream.processAnnotations(GroupData.class);
        String xml = xStream.toXML(groups);

        writeToFile(xml, file);
    }

    private void saveJson(List<GroupData> groups, File file) throws IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        String json = gson.toJson(groups);

        writeToFile(json, file);
    }
}
