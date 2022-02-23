package com.tora.utils.day14;

import io.github.rkamradt.possibly.PossiblyFunction;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ReadingFromMultipleFilesConcurrently {
    @SneakyThrows
    public static void phase2(final List<String> inputFiles, final String text) {
        final Queue<String> matchedLines = new ConcurrentLinkedQueue<>();
        final List<Callable<String>> fileRunners = new ArrayList<>();

        inputFiles.forEach(fileName -> fileRunners.add(
            () -> consumeFile(fileName, matchedLines, text))
        );

        final ExecutorService executorService = Executors.newFixedThreadPool(inputFiles.size());

        final List<Future<String>> results = executorService.invokeAll(fileRunners);

        results.stream()
            .map(PossiblyFunction.of(Future::get))
            .map(p -> p.getValue().orElseThrow())
            .forEach(System.out::println);

        executorService.shutdown();

        System.out.println(matchedLines.size());
    }

    @SneakyThrows
    private static String consumeFile(final String fileName, final Queue<String> matchedLines, final String text) {
        final Path path = Paths.get(fileName);
        Files.lines(path)
            .filter(line -> line.contains(text))
            .forEach(matchedLines::add);
        return "Success for file: " + fileName;
    }
}
