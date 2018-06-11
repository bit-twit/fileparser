package org.bittwit.fileparser.generator;

import org.bittwit.fileparser.config.Config;
import org.bittwit.fileparser.config.ConfigParser;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.*;
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
    private static final Integer DELIMITER_DECIMAL_POINT = 59;
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
                        .map(number -> {
                            String generatedChars = generateASCIICharacters(INTEGER_LIMIT, new HashSet<>(Collections.singleton(DELIMITER_DECIMAL_POINT)));
                            return Arrays.asList(number.toString(), generatedChars, DELIMITER);
                        })
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

    /**
     * Generates integer of max 'limit' ASCII characters between 59 - 126 decimal points.
     *
     * @param limit
     * @param decimalExcluded specify decimal point of characters to exclude
     * @return
     */
    protected String generateASCIICharacters(final Integer limit, final Set<Integer> decimalExcluded) {
        return IntStream.generate(() -> random.nextInt(126))
                .filter(decimalExcluded::contains)
                .limit(limit)
                .boxed()
                .map(number -> Character.valueOf((char)number.byteValue()).toString())
                .collect(Collectors.joining());
    }
}
