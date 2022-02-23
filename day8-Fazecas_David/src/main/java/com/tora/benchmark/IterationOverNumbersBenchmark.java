package com.tora.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class IterationOverNumbersBenchmark {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    @Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
    public void forPrimitive(NumberOrEpochs state) {
        int max = 0;
        for (int i = 1; i <= state.NO_EPOCHS; i++) {
            int aux = i + 1;
            max = max < aux ? aux : max;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    @Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
    public void forWrapper(NumberOrEpochs state) {
        int max = 0;
        for (int i = 1; i <= state.NO_EPOCHS; i++) {
            Integer aux = i + 1;
            max = max < aux ? aux : max;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    @Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
    public void intStream(NumberOrEpochs state) {
        IntStream.rangeClosed(1, state.NO_EPOCHS)
            .map(i -> i + 1)
            .max();
    }

    @State(Scope.Benchmark)
    public static class NumberOrEpochs {
        final public int NO_EPOCHS = 1_000_000;
    }
}
