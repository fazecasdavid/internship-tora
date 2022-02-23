package com.insightfullogic.java8.exercises.chapter3;

import com.insightfullogic.java8.exercises.Exercises;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Advanced Exercises Question 1
 */
public class MapUsingReduce {

    public static <I, O> List<O> map(Stream<I> stream, Function<I, O> mapper) {
        List<O> map = new ArrayList<>();
        return stream.reduce(
            map,
            (List<O> identity, I t) -> {
                List<O> copyMap = new ArrayList<>(identity);
                copyMap.add(mapper.apply(t));
                return copyMap;
            },
            (List<O> m1, List<O> m2) -> {
                List<O> copyMap = new ArrayList<>(m1);
                copyMap.addAll(m2);
                return copyMap;

            }
        );
    }

}
