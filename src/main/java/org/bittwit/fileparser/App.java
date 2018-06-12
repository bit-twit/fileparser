package org.bittwit.fileparser;

import org.bittwit.fileparser.config.Config;
import org.bittwit.fileparser.config.ConfigParser;
import org.bittwit.fileparser.parser.SearchService;

import java.io.File;
import java.io.IOException;

public class App {
    public static final String CONFIG_FILE = "max.conf";
    public static final String RESULT_FILE = "max.out";

    public static void main(String[] args) {
        System.out.println("Started application.");
        File configFile = new File(relativeToWorkingPath(CONFIG_FILE));
        Config config = ConfigParser.parse(configFile);
        File outputFile = new File(relativeToWorkingPath(RESULT_FILE));
        createNew(outputFile);
        SearchService service = new SearchService(config, outputFile,  4);
        service.computeMax();
    }

    public static String relativeToWorkingPath(final String fileName) {
        return System.getProperty("user.dir") + File.separator + fileName;
    }

    public static void createNew(final File outputFile) {
        if (!outputFile.exists()) {
            File parentFolder = outputFile.getParentFile();
            if (!parentFolder.exists()) {
                boolean result = parentFolder.mkdirs();
                if (!result) {
                    throw new RuntimeException(String.format("Failed created path for file: %s", parentFolder));
                }
            }
            try {
                if (outputFile.exists()) {
                    outputFile.delete();
                }
                outputFile.createNewFile();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
