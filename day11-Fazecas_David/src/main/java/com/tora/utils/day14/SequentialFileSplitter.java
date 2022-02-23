package com.tora.utils.day14;

import lombok.SneakyThrows;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static com.tora.utils.day14.MultithreadedFileSplitter.readPartOfFile;

public class SequentialFileSplitter {

    @SneakyThrows
    public static void phase0(String inputFileName, String outputFileName, int numSplits) {
        RandomAccessFile raf = new RandomAccessFile(inputFileName, "r");
        long sourceSize = raf.length();
        raf.close();

        long bytesPerSplit = sourceSize / numSplits;
        long remainingBytes = sourceSize % numSplits;

        final List<Supplier<String>> fileRunners = new ArrayList<>();

        IntStream.range(0, numSplits)
            .forEach(idx -> fileRunners.add(
                () -> readPartOfFile(inputFileName, outputFileName + idx, idx * bytesPerSplit, bytesPerSplit))
            );

        if (remainingBytes > 0) {
            fileRunners.add(
                () -> readPartOfFile(inputFileName, outputFileName + numSplits, numSplits * bytesPerSplit, remainingBytes)
            );
        }

        fileRunners.stream()
            .map(Supplier::get)
            .forEach(System.out::println);
    }

}
