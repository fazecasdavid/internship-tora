package com.tora;

import java.util.Collection;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Util {

    public static List<String> mapToUppercase(List<String> input) {
//        throw new RuntimeException();
        return input.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
    }

    public static List<String> removeElementsWithMoreThanFourCharacters(List<String> input) {
//        throw new RuntimeException();
        return input.stream()
            .filter(el -> el.length() < 4)
            .collect(Collectors.toList());
    }

    public static List<String> sortStrings(List<String> input) {
//        throw new RuntimeException();
        return input.stream()
            .sorted()
            .collect(Collectors.toList());
    }

    public static List<Integer> sortIntegers(List<String> input) {
//        throw new RuntimeException();
        return input.stream()
            .map(Integer::parseInt)
            .sorted()
            .collect(Collectors.toList());
    }

    public static List<Integer> sortIntegersDescending(List<String> input) {
//        throw new RuntimeException();
        return input.stream()
            .map(Integer::parseInt)
            .sorted(Comparator.reverseOrder())
            .collect(Collectors.toList());
    }

    public static Integer sum(List<Integer> numbers) {
//        throw new RuntimeException();
        return numbers.stream()
            .reduce(0, Integer::sum);
    }

    public static List<String> flattenToSingleCollection(List<List<String>> input) {
//        throw new RuntimeException();
        return input.stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    public static String separateNamesByComma(List<Person> input) {
//        throw new RuntimeException();
        return input.stream()
            .map(Person::getName)
//            .reduce("Names: ", (identity, el) -> identity + ", " + el);
            .collect(Collectors.joining(", ", "Names: ", "."));
    }

    public static String findNameOfOldestPerson(List<Person> input) {
//        throw new RuntimeException();
        return input.stream()
            .max(Comparator.comparingInt(Person::getAge))
            .get()
            .getName();
    }

    public static List<String> filterPeopleLessThan18YearsOld(List<Person> input) {
//        throw new RuntimeException();
        return input.stream()
            .filter(el -> el.getAge() < 18)
            .map(Person::getName)
            .collect(Collectors.toList());
    }

    public static IntSummaryStatistics getSummaryStatisticsForAge(List<Person> input) {
//        throw new RuntimeException();
        return input.stream()
            .map(Person::getAge)
            .collect(IntSummaryStatistics::new, IntSummaryStatistics::accept, IntSummaryStatistics::combine);
    }


    public static Map<Boolean, List<Person>> partitionAdults(List<Person> input) {
//        throw new RuntimeException();
        return input.stream()
            .collect(Collectors.partitioningBy((el) -> el.getAge() > 18));
    }

    public static Map<String, List<Person>> partitionByNationality(List<Person> input) {
//        throw new RuntimeException();
        return input.stream()
            .collect(Collectors.groupingBy(Person::getCountry));
    }
}
