package org.bittwit.fileparser.parser;

import java.io.File;
import java.util.function.Supplier;

class MaxSearcher implements Supplier<SearchResult> {
    private final File inputFile;

    public MaxSearcher(File inputFile) {
        this.inputFile = inputFile;
    }

    @Override
    public SearchResult get() {
        System.out.println(String.format("Searching in file: %s", inputFile.getName()));
        return new SearchResult(inputFile, 0);
    }
}
