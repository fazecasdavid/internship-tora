package com.tora;

import com.tora.benchmark.ArrayListRepositoryBenchmark;
import com.tora.benchmark.FastutilListRepositoryBenchmark;
import com.tora.benchmark.FastutilSetRepositoryBenchmark;
import com.tora.benchmark.HashSetRepositoryBenchmark;
import com.tora.benchmark.IterationOverNumbersBenchmark;
import com.tora.benchmark.TreeSetRepositoryBenchmark;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class Main {
    public static void main(String[] args) throws Exception {
        final Options opt = new OptionsBuilder()
//            .include(ArrayListRepositoryBenchmark.class.getSimpleName())
//            .include(HashSetRepositoryBenchmark.class.getSimpleName())
//            .include(TreeSetRepositoryBenchmark.class.getSimpleName())
//            .include(FastutilListRepositoryBenchmark.class.getSimpleName())
//            .include(FastutilSetRepositoryBenchmark.class.getSimpleName())
            .include(IterationOverNumbersBenchmark.class.getSimpleName())
//            .addProfiler(StackProfiler.class)
            .build();

        new Runner(opt).run();

    }
}
