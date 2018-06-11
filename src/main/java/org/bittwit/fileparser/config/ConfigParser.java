package org.bittwit.fileparser.config;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigParser {

    public static Config parse(final File configFile) {
        if (!configFile.exists()) {
            throw new RuntimeException(String.format("Config file not found: %s", configFile));
        }
        try (BufferedReader fileReader = new BufferedReader(new FileReader(configFile))) {
            List<File> fileList = fileReader.lines()
                    .map(fileName -> new File(fileName))
                    .collect(Collectors.toList());
            if (fileList.size() == 0) {
                throw new RuntimeException("File list empty!");
            }
            return new Config(fileList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
