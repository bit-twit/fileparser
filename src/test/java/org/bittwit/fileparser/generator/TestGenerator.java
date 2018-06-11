package org.bittwit.fileparser.generator;

import org.bittwit.fileparser.config.Config;
import org.bittwit.fileparser.config.ConfigParser;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.bittwit.fileparser.App.relativeToWorkingPath;

/**
 * Helper class to generate tests files.
 * Uses same config file as main application.
 */
public class TestGenerator {
    private static final String CONFIG_FILE = "max.conf";
    private static final Integer INTEGER_LIMIT = 1000;
    private static final String DELIMITER = ";";
    private Random random = new Random();

    @Test
    public void generateFiles() {
        List<File> results = generateFilesFromConfig(CONFIG_FILE);
        System.out.println("Generated files: ");
        for (File file : results) {
            System.out.println(file.getAbsolutePath());
        }
    }

    public List<File> generateFilesFromConfig(final String configPath) {
        Config config = ConfigParser.parse(new File(relativeToWorkingPath(configPath)));
        List<File> processedFiles = new ArrayList<>();
        for (File outputFile : config.getFiles()) {
            createIfNecessary(outputFile);
            try(PrintWriter pw = new PrintWriter(Files.newBufferedWriter(outputFile.toPath()))) {
                generateIntegers(random.nextInt(INTEGER_LIMIT)).stream()
                        .map(number -> Arrays.asList(number.toString(), generateString(INTEGER_LIMIT), DELIMITER))
                        .flatMap(stringList -> stringList.stream())
                        .forEach(pw::print);
                processedFiles.add(outputFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return processedFiles;
    }

    private void createIfNecessary(final File outputFile) {
        if (!outputFile.exists()) {
            File parentFolder = outputFile.getParentFile();
            if (!parentFolder.exists()) {
                boolean result = parentFolder.mkdirs();
                if (!result) {
                    throw new RuntimeException(String.format("Failed created path for file: %s", parentFolder));
                }
            }
            try {
                outputFile.createNewFile();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected List<Integer> generateIntegers(final Integer limit) {
        return IntStream.generate(() -> random.nextInt(Integer.MAX_VALUE))
                .limit(limit)
                .boxed()
                .collect(Collectors.toList());
    }

    protected String generateString(final Integer limit) {
        return IntStream.generate(() -> random.nextInt(126))
                .filter(number -> number > 59) // filter greater than decimal for ';'
                .limit(limit)
                .boxed()
                .map(number -> Character.valueOf((char)number.byteValue()).toString())
                .collect(Collectors.joining());
    }
}
