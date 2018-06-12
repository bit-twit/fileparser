package org.bittwit.fileparser.parser;

import org.bittwit.fileparser.config.Config;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SearchService {
    private final Config fileConfig;
    private final File outputFile;
    private final Integer numberOfWorkers;

    public SearchService(final Config fileConfig, final File outputFile, final Integer numberOfWorkers) {
        this.fileConfig = fileConfig;
        this.outputFile = outputFile;
        this.numberOfWorkers = numberOfWorkers;
    }

    public void computeMax() {
        System.out.println(String.format("Starting search with %d workers.", numberOfWorkers));
        ExecutorService executor = Executors.newFixedThreadPool(numberOfWorkers);

        List<CompletableFuture<SearchResult>> tasks = fileConfig.getFiles().stream()
                .map(file -> scheduleSearch(executor, new MaxSearcher(file)::get))
                .map(future -> {
                    future.handle((result, error) -> {
                        if (error != null) {
                            System.out.println(String.format("Error while running search task : %s", error.getMessage()));
                            return 0;
                        }
                        return result;
                    });
                    return future;
                })
                .collect(Collectors.toList());

        // wait for all futures to complete
        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[tasks.size()])).join();

        // get result from futures
        Optional<SearchResult> max = tasks.stream()
                .map(task -> {
                    try {
                        return task.get();
                    } catch (InterruptedException | ExecutionException e) {
                        System.out.println(String.format("Error while getting future results: %s", e.getMessage()));
                    }
                    return null;
                })
                .filter(result -> result != null)
                .reduce((searchResult, searchResult2) -> {
                    if (searchResult.getMax() > searchResult2.getMax()) {
                        return searchResult;
                    } else {
                        return searchResult2;
                    }
                });
        if (!max.isPresent()) {
            throw new RuntimeException("No max found!");
        }
        System.out.println(String.format("Max is: %s", max.get()));
        writeMaxToOutput(max.get());
    }

    protected void writeMaxToOutput(final SearchResult searchResult) {
        try(PrintWriter pw = new PrintWriter(Files.newBufferedWriter(outputFile.toPath()))) {
            pw.println(String.format("Maximum: %d", searchResult.getMax()));
            pw.println(String.format("Location: %s", searchResult.getFile().getAbsolutePath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected CompletableFuture<SearchResult> scheduleSearch(final Executor executor, final Supplier<SearchResult> supplier) {
        return CompletableFuture.supplyAsync(supplier, executor);
    }

}
