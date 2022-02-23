package com.tora.benchmark;

import com.tora.domain.Order;
import com.tora.reposiotry.InMemoryRepository;
import com.tora.reposiotry.impl.fastutil.FastutilListBasedRepository;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class FastutilListRepositoryBenchmark {
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    @Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 100, time = 10, timeUnit = TimeUnit.MILLISECONDS)
    public void testAdd(FastutilListRepositoryState state) {
        int nextId = state.currentId + state.NO_EPOCHS;
        state.repository.add(Order.builder().id(nextId).price(nextId).quantity(nextId).build());
        state.currentId++;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    @Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 100, time = 10, timeUnit = TimeUnit.MILLISECONDS)
    public void testContains(FastutilListRepositoryState state) {
        int nextId = state.NO_EPOCHS - state.currentId;
        if(state.currentId % 2 == 0){
            nextId *= 2;
        }
        state.repository.contains(Order.builder().id(nextId).price(nextId).quantity(nextId).build());
        state.currentId++;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(1)
    @Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 100, time = 10, timeUnit = TimeUnit.MILLISECONDS)
    public void testRemove(FastutilListRepositoryState state) {
        int nextId = state.NO_EPOCHS - state.currentId;
        if(state.currentId % 2 == 0){
            nextId *= 2;
        }
        state.repository.remove(Order.builder().id(nextId).price(nextId).quantity(nextId).build());
        state.currentId++;
    }

    @State(Scope.Benchmark)
    public static class FastutilListRepositoryState {
        final public int NO_EPOCHS = 1_000_000;
        public int currentId;
        public InMemoryRepository<Order> repository;


        @Setup(Level.Iteration)
        public void doSetup() {
            System.out.println("Entering Setup for ArrayList");
            repository = new FastutilListBasedRepository<>();
            currentId = 0;
            IntStream.range(0, NO_EPOCHS).forEach(
                el -> repository.add(Order.builder().id(el).price(el).quantity(el).build())
            );
        }

        @TearDown(Level.Iteration)
        public void doTearDown() {
            System.out.println("Entering TearDown for ArrayList");
        }
    }
}
