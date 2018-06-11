package org.bittwit.fileparser.config;

import java.io.File;
import java.util.List;

public class Config {
    private final List<File> files;

    public Config(final List<File> files) {
        this.files = files;
    }

    public List<File> getFiles() {
        return this.files;
    }
}
