package com.insightfullogic.java8.exercises.chapter3;

import com.insightfullogic.java8.exercises.Exercises;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Advanced Exercises Question 2
 */
public class FilterUsingReduce {

    public static <I> List<I> filter(Stream<I> stream, Predicate<I> predicate) {
//        return stream.filter(predicate).collect(Collectors.toList());
        return stream.reduce(new ArrayList<I>(), (List<I> id, I el) -> {
            List<I> newL = new ArrayList<>(id);
            if (predicate.test(el))
                newL.add(el);
            return newL;
        }, (List<I> l1, List<I> l2) -> {
            List<I> newL = new ArrayList<>(l1);
            newL.addAll(l2);
            return newL;
        });
    }

}
