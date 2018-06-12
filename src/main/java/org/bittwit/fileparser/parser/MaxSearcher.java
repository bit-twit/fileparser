package org.bittwit.fileparser.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.function.Supplier;
import java.util.stream.Stream;

class MaxSearcher implements Supplier<SearchResult> {
    private final File inputFile;

    public MaxSearcher(File inputFile) {
        this.inputFile = inputFile;
    }

    @Override
    public SearchResult get() {
        System.out.println(String.format("Searching in file: %s", inputFile.getName()));
        try (BufferedReader fileReader = new BufferedReader(new FileReader(inputFile))) {
            OptionalInt maxFromFile = fileReader.lines()
                    .flatMap(MaxSearcher::getIntegersFromLine)
                    .mapToInt(Integer::intValue).max();
            if (!maxFromFile.isPresent()) {
                return new SearchResult(inputFile, 0);
            }
            return new SearchResult(inputFile, maxFromFile.getAsInt());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected static Stream<Integer> getIntegersFromLine(final String line) {
       return Arrays.stream(line.split(";"))
                .flatMap(token -> Arrays.stream(token
                        .replaceAll("[^0-9]", " ")
                        .replaceAll("\\s+", " ")
                        .split(" "))
                        .filter(str -> !str.isEmpty())
                        .map(MaxSearcher::tryToGetInt)
                );
    }

    protected static Integer tryToGetInt(final String possibleInt) {
        try {
            return Integer.parseInt(possibleInt);
        } catch (Exception e) {
            System.out.println(String.format("Failed to parse [%s] as int.", possibleInt));
        }
        return 0;
    }
}
