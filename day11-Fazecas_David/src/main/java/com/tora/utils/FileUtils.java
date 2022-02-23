package com.tora.utils;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileUtils {
    @SneakyThrows
    public static void findLinesWithText(final String filePath, final String text, long limit) {
        Path path = Paths.get(filePath);
        Files.lines(path)
            .filter(line -> line.contains(text))
            .limit(limit)
            .forEach(System.out::println);
    }

    @SneakyThrows
    public static void countNoOfOccurrences(final String filePath, final String text) {
        Path path = Paths.get(filePath);
        int count = Files.lines(path).parallel()
            .filter(line -> line.contains(text))
            .mapToInt(line -> StringUtils.countMatches(line, text))
            .sum();
        System.out.println(count);
    }

    @SneakyThrows
    public static void countLines(final String filePath) {
        Path path = Paths.get(filePath);
        long count = Files.lines(path)
            .count();
        System.out.println(count);
    }

    @SneakyThrows
    public static void mostFrequentUsedSubject(final String filePath) {
        Path path = Paths.get(filePath);
        var mostCommon = Files
            .lines(path)
            .filter(line -> line.contains("subject="))
            .map(line -> StringUtils.substringBetween(line, "subject=", ","))
            .filter(Objects::nonNull)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue()).orElseThrow();

        System.out.println(mostCommon);
    }

    @SneakyThrows
    public static void mostFrequentWords(final String filePath) {
        // way to slow
        Path path = Paths.get(filePath);
        var mostCommon = Files
            .lines(path)
            .flatMap(line -> Arrays.stream(line.split("[^a-zA-Z0-9']+")))
            .filter(Objects::nonNull)
            .filter(str -> str.length() > 3)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue()).orElseThrow();

        System.out.println(mostCommon);
    }


}
