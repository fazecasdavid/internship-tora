package com.tora;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static long epochs = 1_000_000L;
    public static long precision = 100_000L;

    public static void main(String[] args) {
        time_testMapPut();
        System.out.println();

        time_testMapGet();
        System.out.println();

        time_testMapContainsKey();
        System.out.println();
    }

    private static void testMapPut(Map<Integer, Integer> map) {
        new Random()
            .ints(epochs)
            .forEach((el) -> map.put(el, el + 5));
    }

    private static void testMapGet(Map<Integer, Integer> map) {
        new Random()
            .ints(epochs)
            .forEach(map::get);
    }

    private static void testMapContainsKey(Map<Integer, Integer> map) {
        new Random()
            .ints(epochs)
            .forEach(map::containsKey);
    }

    private static void time_testMapPut() {
        long testHashMapPut_Start = System.nanoTime();
        testMapPut(new HashMap<>());
        long testHashMapPut_Time = System.nanoTime() - testHashMapPut_Start;

        long testConcurrentHashMapPut_Start = System.nanoTime();
        testMapPut(new ConcurrentHashMap<>());
        long testConcurrentHashMapPut_Time = System.nanoTime() - testConcurrentHashMapPut_Start;

        long testLinkedHashMapPut_Start = System.nanoTime();
        testMapPut(new LinkedHashMap<>());
        long testLinkedHashMapPut_Time = System.nanoTime() - testLinkedHashMapPut_Start;

        System.out.println("testHashMapPut: " + testHashMapPut_Time / precision);
        System.out.println("testConcurrentHashMapPut: " + testConcurrentHashMapPut_Time / precision);
        System.out.println("testLinkedHashMapPut: " + testLinkedHashMapPut_Time / precision);
    }

    private static void time_testMapGet() {
        Map<Integer, Integer> map = new HashMap<>();
        testMapPut(map);

        long testHashMapGet_Start = System.nanoTime();
        testMapGet(map);
        long testHashMapGet_Time = System.nanoTime() - testHashMapGet_Start;


        map = new ConcurrentHashMap<>();
        testMapPut(map);
        long testConcurrentHashMapGet_Start = System.nanoTime();
        testMapGet(map);
        long testConcurrentHashMapGet_Time = System.nanoTime() - testConcurrentHashMapGet_Start;

        map = new LinkedHashMap<>();
        testMapPut(map);
        long testLinkedHashMapGet_Start = System.nanoTime();
        testMapGet(map);
        long testLinkedHashMapGet_Time = System.nanoTime() - testLinkedHashMapGet_Start;

        System.out.println("testHashMapGet: " + testHashMapGet_Time / precision);
        System.out.println("testConcurrentHashMapGet: " + testConcurrentHashMapGet_Time / precision);
        System.out.println("testLinkedHashMapGet: " + testLinkedHashMapGet_Time / precision);
    }

    private static void time_testMapContainsKey() {
        Map<Integer, Integer> map = new HashMap<>();
        testMapPut(map);

        long testHashMapContainsKey_Start = System.nanoTime();
        testMapContainsKey(map);
        long testHashMapContainsKey_Time = System.nanoTime() - testHashMapContainsKey_Start;


        map = new ConcurrentHashMap<>();
        testMapPut(map);
        long testConcurrentHashMapContainsKey_Start = System.nanoTime();
        testMapContainsKey(map);
        long testConcurrentHashMapContainsKey_Time = System.nanoTime() - testConcurrentHashMapContainsKey_Start;

        map = new LinkedHashMap<>();
        testMapPut(map);
        long testLinkedHashMapContainsKey_Start = System.nanoTime();
        testMapContainsKey(map);
        long testLinkedHashMapContainsKey_Time = System.nanoTime() - testLinkedHashMapContainsKey_Start;

        System.out.println("testHashMapContainsKey: " + testHashMapContainsKey_Time / precision);
        System.out.println("testConcurrentHashMapContainsKey: " + testConcurrentHashMapContainsKey_Time / precision);
        System.out.println("testLinkedHashMapContainsKey: " + testLinkedHashMapContainsKey_Time / precision);
    }
}
