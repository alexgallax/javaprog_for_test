package addressbook.generators;

import com.beust.jcommander.Parameter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class GeneratorBase {

    @Parameter(names = "-c", description = "Number of generated items")
    protected int count;

    @Parameter(names = "-f", description = "Target file")
    protected String filePath;

    @Parameter(names = "-d", description = "Data format")
    protected String format;

    protected void writeToFile(String data, File file) throws IOException {
        Writer writer = new FileWriter(file);
        writer.write(data);
        writer.close();
    }
}
