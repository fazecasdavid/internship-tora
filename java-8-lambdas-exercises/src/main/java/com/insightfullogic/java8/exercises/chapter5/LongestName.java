package com.insightfullogic.java8.exercises.chapter5;

import com.insightfullogic.java8.examples.chapter1.Artist;
import com.insightfullogic.java8.exercises.Exercises;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;

public class LongestName {

    public static Artist byReduce(List<Artist> artists) {
        return artists.stream()
            .reduce((artist, artist2) -> artist.getName().length() - artist2.getName().length() > 0? artist :artist2).get();
    }

    public static Artist byCollecting(List<Artist> artists) {
        return artists.stream().max(comparingInt(value -> (int) value.getName().length())).get();
    }

}
