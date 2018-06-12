package org.bittwit.fileparser.parser;

import java.io.File;

class SearchResult {
    private final File file;
    private final Integer max;

    public SearchResult(File file, Integer max) {
        this.file = file;
        this.max = max;
    }

    public File getFile() {
        return file;
    }

    public Integer getMax() {
        return max;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "file=" + file +
                ", max=" + max +
                '}';
    }
}
