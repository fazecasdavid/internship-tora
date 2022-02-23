package com.insightfullogic.java8.exercises.chapter3;

import com.insightfullogic.java8.examples.chapter1.Artist;
import com.insightfullogic.java8.exercises.Exercises;

import java.util.List;
import java.util.function.Function;

public class Question2 {
    // Q3
    public static int countBandMembersInternal(List<Artist> artists) {
        long count =  artists.stream()
            .map(artist -> artist.getMembers().count())
            .mapToLong(el -> el)
            .sum();
        return (int) count;
    }
}
