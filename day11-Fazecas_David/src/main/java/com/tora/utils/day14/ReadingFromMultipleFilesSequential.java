package com.tora.utils.day14;

import io.github.rkamradt.possibly.PossiblyConsumer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadingFromMultipleFilesSequential {
    public static void phase1(final List<String> inputFiles, final String text) {
        final List<String> matchedLines = new ArrayList<>();

        inputFiles.forEach(PossiblyConsumer.of(
            fileName -> {
                Path path = Paths.get(fileName);
                Files.lines(path)
                    .filter(line -> line.contains(text))
                    .forEach(matchedLines::add);
            })
        );

        System.out.println(matchedLines.size());
    }
}
