package com.tora.utils.day14;

import io.github.rkamradt.possibly.PossiblyFunction;
import lombok.SneakyThrows;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class MultithreadedFileSplitter {

    @SneakyThrows
    public static void phase0(String inputFileName, String outputFileName, int numSplits) {
        RandomAccessFile raf = new RandomAccessFile(inputFileName, "r");
        long sourceSize = raf.length();
        raf.close();

        long bytesPerSplit = sourceSize / numSplits;
        long remainingBytes = sourceSize % numSplits;

        final List<Callable<String>> fileRunners = new ArrayList<>();

        IntStream.range(0, numSplits)
            .forEach(idx -> fileRunners.add(
                () -> readPartOfFile(inputFileName, outputFileName + idx, idx * bytesPerSplit, bytesPerSplit))
            );

        if (remainingBytes > 0) {
            fileRunners.add(
                () -> readPartOfFile(inputFileName, outputFileName + numSplits, numSplits * bytesPerSplit, remainingBytes)
            );
        }
        ExecutorService executorService = Executors.newFixedThreadPool(numSplits);
        var results = executorService.invokeAll(fileRunners);

        results.stream()
            .map(PossiblyFunction.of(Future::get))
            .map(p -> p.getValue().orElseThrow())
            .forEach(System.out::println);

        executorService.shutdown();
    }

    @SneakyThrows
    public static String readPartOfFile(String inputFileName, String outputFileName, long starReadingPos, long nrOfBytes) {
        RandomAccessFile raf = new RandomAccessFile(inputFileName, "r");
        raf.seek(starReadingPos);

        int maxReadBufferSize = 32 * 1024; //32 KB
        BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(outputFileName));
        if (nrOfBytes > maxReadBufferSize) {
            long numReads = nrOfBytes / maxReadBufferSize;
            long numRemainingRead = nrOfBytes % maxReadBufferSize;
            for (int i = 0; i < numReads; i++) {
                readWrite(raf, bw, maxReadBufferSize);
            }
            if (numRemainingRead > 0) {
                readWrite(raf, bw, numRemainingRead);
            }
        } else {
            readWrite(raf, bw, nrOfBytes);
        }
        bw.close();
        return "Success for file: " + outputFileName;
    }

    @SneakyThrows
    private static void readWrite(RandomAccessFile raf, BufferedOutputStream bw, long numBytes) {
        byte[] buf = new byte[(int) numBytes];
        int val = raf.read(buf);
        if (val != -1) {
            bw.write(buf);
        }
    }
}
