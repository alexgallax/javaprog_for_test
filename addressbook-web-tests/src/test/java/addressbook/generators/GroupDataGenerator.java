package addressbook.generators;

import addressbook.model.GroupData;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator {

    @Parameter(names = "-c", description = "Number of groups")
    private int count;

    @Parameter(names = "-f", description = "Target file")
    private String filePath;

    @Parameter(names = "-d", description = "Data format")
    private String format;

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
        Writer writer = new FileWriter(file);
        for (GroupData group : groups) {
            writer.write(String.format("%s;%s;%s\n",
                    group.getGroupName(),
                    group.getGroupHeader(),
                    group.getGroupFooter()));
        }
        writer.close();
    }

    private void saveXml(List<GroupData> groups, File file) throws IOException {
        XStream xStream = new XStream();
        xStream.processAnnotations(GroupData.class);
        String xml = xStream.toXML(groups);

        Writer writer = new FileWriter(file);
        writer.write(xml);
        writer.close();
    }

    private void saveJson(List<GroupData> groups, File file) throws IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        String json = gson.toJson(groups);

        Writer writer = new FileWriter(file);
        writer.write(json);
        writer.close();
    }
}
